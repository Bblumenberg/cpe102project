import point
import actions
import worldmodel

class Entity:#Parent
   def __init__(self, name, imgs):
      self.name = name
      self.imgs = imgs
      self.current_img = 0
   def get_images(self):
      return self.imgs
   def get_image(self):
      return self.imgs[self.current_img]
   def get_name(self):
      return self.name
   def next_image(self):
      self.current_img = (self.current_img + 1) % len(self.imgs)

class Background(Entity):
   pass

class PositionedEntity(Entity):#Parent
   def __init__(self, name, imgs, position, type):
      Entity.__init__(self, name, imgs)
      self.position = position
      self.type = type
   def set_position(self, point):
      self.position = point
   def get_position(self):
      return self.position
   def entity_string(self):
      return ' '.join([str(self.type), self.name, str(self.position.x), str(self.position.y)])


class Obstacle(PositionedEntity):
   def __init__(self, name, imgs, position):
      PositionedEntity.__init__(self, name, imgs, position, 'obstacle')

class ActionedEntity(PositionedEntity):#Parent
   def __init__(self, name, imgs, position, rate, type):
      PositionedEntity.__init__(self, name, imgs, position, type)
      self.rate = rate
      self.pending_actions = []
   def remove_pending_action(self, action):
      self.pending_actions.remove(action)
   def add_pending_action(self, action):
      self.pending_actions.append(action)
   def get_pending_actions(self):
      return self.pending_actions
   def clear_pending_actions(self):
      self.pending_actions = []
   def get_rate(self):
      return self.rate

class Vein(ActionedEntity):
   def __init__(self, name, rate, position, imgs, resource_distance=1):
      ActionedEntity.__init__(self, name, imgs, position, rate, 'vein')
      self.resource_distance = resource_distance
   def get_resource_distance(self):
      return self.resource_distance
   def find_open_around(self, world, pt, distance):
      for dy in range(-distance, distance + 1):
         for dx in range(-distance, distance + 1):
            new_pt = point.Point(pt.x + dx, pt.y + dy)
            if (world.within_bounds(new_pt) and (not world.is_occupied(new_pt))):
               return new_pt
      return None
   def create_action(self, world, i_store):
      def action(current_ticks):
         self.remove_pending_action(action)
         open_pt = self.find_open_around(world, self.position, self.resource_distance)
         if open_pt:
            ore = actions.create_ore(world, "ore - " + self.name + " - " + str(current_ticks), open_pt, current_ticks, i_store)
            world.add_entity(ore)
            tiles = [open_pt]
         else:
            tiles = []
         actions.schedule_action(world, self, self.create_action(world, i_store), current_ticks + self.rate)
         return tiles
      return action
   def schedule_any(self, world, ticks, i_store):
      actions.schedule_action(world, self, self.create_action(world, i_store), ticks + self.rate)

class Ore(ActionedEntity):
   def __init__(self, name, position, imgs, rate=5000):
      ActionedEntity.__init__(self, name, imgs, position, rate, 'ore')
   def create_ore_transform_action(self, world, i_store):
      def action(current_ticks):
         self.remove_pending_action(action)
         blob = actions.create_blob(world, self.name + " -- blob", self.position, self.rate // actions.BLOB_RATE_SCALE, current_ticks, i_store)
         actions.remove_entity(world, self)
         world.add_entity(blob)
         return [blob.get_position()]
      return action
   def schedule_any(self, world, ticks, i_store):
      actions.schedule_action(world, self, self.create_ore_transform_action(world, i_store), ticks + self.rate)

class OreBlob(ActionedEntity):
   def __init__(self, name, position, rate, imgs, animation_rate):
      ActionedEntity.__init__(self, name, imgs, position, rate, None)
      self.animation_rate = animation_rate
   def get_animation_rate(self):
      return self.animation_rate
   def blob_next_position(self, world, dest_pt):
      horiz = actions.sign(dest_pt.x - self.position.x)
      new_pt = point.Point(self.position.x + horiz, self.position.y)
      if horiz == 0 or (world.is_occupied(new_pt) and not isinstance(world.get_tile_occupant(new_pt), Ore)):
         vert = actions.sign(dest_pt.y - self.position.y)
         new_pt = point.Point(self.position.x, self.position.y + vert)
         if vert == 0 or (world.is_occupied(new_pt) and not isinstance(world.get_tile_occupant(new_pt), Ore)):
            new_pt = self.position
      return new_pt
   def blob_to_vein(self, world, vein):
      if not vein:
         return ([self.position], False)
      vein_pt = vein.get_position()
      if self.position.adjacent(vein_pt):
         actions.remove_entity(world, vein)
         return ([vein_pt], True)
      else:
         new_pt = self.blob_next_position(world, vein_pt)
         old_entity = world.get_tile_occupant(new_pt)
         if isinstance(old_entity, Ore):
            actions.remove_entity(world, old_entity)
         return (world.move_entity(self, new_pt), False)
   def create_action(self, world, i_store):
      def action(current_ticks):
         self.remove_pending_action(action)
         vein = world.find_nearest(self.position, Vein)
         (tiles, found) = self.blob_to_vein(world, vein)
         next_time = current_ticks + self.rate
         if found:
            quake = actions.create_quake(world, tiles[0], current_ticks, i_store)
            world.add_entity(quake)
            next_time = current_ticks + self.rate * 2
         actions.schedule_action(world, self, self.create_action(world, i_store), next_time)
         return tiles
      return action
   def schedule_blob(self, world, ticks, i_store):
      actions.schedule_action(world, self, self.create_action(world, i_store), ticks + self.rate)
      actions.schedule_animation(world, self)

class Quake(ActionedEntity):
   def __init__(self, name, position, imgs, animation_rate):
      ActionedEntity.__init__(self, name, imgs, position, None, None)
      self.animation_rate = animation_rate
   def get_animation_rate(self):
      return self.animation_rate
   def create_entity_death_action(self, world):
      def action(current_ticks):
         self.remove_pending_action(action)
         actions.remove_entity(world, self)
         return [self.position]
      return action
   def schedule_quake(self, world, ticks):
      actions.schedule_animation(world, self, actions.QUAKE_STEPS)
      actions.schedule_action(world, self, self.create_entity_death_action(world), ticks + actions.QUAKE_DURATION)

class ResourceEntity(ActionedEntity):#Parent
   def __init__(self, name, imgs, position, rate, resource_limit, resource_count, type):
      ActionedEntity.__init__(self, name, imgs, position, rate, type)
      self.resource_limit = resource_limit
      self.resource_count = resource_count
   def set_resource_count(self, n):
      self.resource_count = n
   def get_resource_count(self):
      return self.resource_count
   def get_resource_limit(self):
      return self.resource_limit

class Blacksmith(ResourceEntity):
   def __init__(self, name, position, imgs, resource_limit, rate,
                resource_distance=1):
      ResourceEntity.__init__(self, name, imgs, position, rate, resource_limit, 0, 'blacksmith')
      self.resource_distance = resource_distance
   def get_resource_distance(self):
      return self.resource_distance

class Miner(ResourceEntity):
   def __init__(self, name, resource_limit, position, rate, imgs, animation_rate, resource_count, type):
      ResourceEntity.__init__(self, name, imgs, position, rate, resource_limit, resource_count, type)
      self.animation_rate = animation_rate
   def get_animation_rate(self):
      return self.animation_rate
   def next_position(self, world, dest_pt):
      entity_pt = self.position
      horiz = actions.sign(dest_pt.x - entity_pt.x)
      new_pt = point.Point(entity_pt.x + horiz, entity_pt.y)
      if horiz == 0 or world.is_occupied(new_pt):
         vert = actions.sign(dest_pt.y - entity_pt.y)
         new_pt = point.Point(entity_pt.x, entity_pt.y + vert)
         if vert == 0 or world.is_occupied(new_pt):
            new_pt = point.Point(entity_pt.x, entity_pt.y)
      return new_pt

class MinerNotFull(Miner):
   def __init__(self, name, resource_limit, position, rate, imgs, animation_rate):
      Miner.__init__(self, name, resource_limit, position, rate, imgs, animation_rate, 0, 'miner')
   def miner_to_ore(self, world, ore):
      if not ore:
         return ([self.position], False)
      ore_pt = ore.get_position()
      if self.position.adjacent(ore_pt):
         self.resource_count += 1
         actions.remove_entity(world, ore)
         return ([ore_pt], True)
      else:
         new_pt = self.next_position(world, ore_pt)
         return (world.move_entity(self, new_pt), False)
   def create_miner_action(self, world, i_store):
      def action(current_ticks):
         self.remove_pending_action(action)
         ore = world.find_nearest(self.position, Ore)
         (tiles, found) = self.miner_to_ore(world, ore)
         new_entity = self
         if found:
            new_entity = actions.try_transform_miner(world, self, self.try_transform_miner_not_full)
         actions.schedule_action(world, new_entity, new_entity.create_miner_action(world, i_store), current_ticks + new_entity.get_rate())
         return tiles
      return action
   def try_transform_miner_not_full(self, world):
      if self.resource_count < self. resource_limit:
         return self
      else:
         new_entity = MinerFull(self.name, self.resource_limit, self.position, self.rate, self.imgs, self.animation_rate)
         return new_entity
   def schedule_any(self, world, ticks, i_store):
      actions.schedule_action(world, self, self.create_miner_action(world, i_store), ticks + self.rate)
      actions.schedule_animation(world, self)

class MinerFull(Miner):
   def __init__(self, name, resource_limit, position, rate, imgs, animation_rate):
      Miner.__init__(self, name, resource_limit, position, rate, imgs, animation_rate, resource_limit, None)
      self.resource_count = resource_limit
   def miner_to_smith(self, world, smith):
      if not smith:
         return ([self.position], False)
      smith_pt = smith.get_position()
      if self.position.adjacent(smith_pt):
         smith.set_resource_count(smith.get_resource_count() + self.resource_count)
         self.resource_count = 0
         return ([], True)
      else:
         new_pt = self.next_position(world, smith_pt)
         return (world.move_entity(self, new_pt), False)
   def create_miner_action(self, world, i_store):
      def action(current_ticks):
         self.remove_pending_action(action)
         smith = world.find_nearest(self.position, Blacksmith)
         (tiles, found) = self.miner_to_smith(world, smith)
         new_entity = self
         if found:
            new_entity = actions.try_transform_miner(world, self, self.try_transform_miner_full)
         actions.schedule_action(world, new_entity, new_entity.create_miner_action(world, i_store), current_ticks + new_entity.get_rate())
         return tiles
      return action
   def try_transform_miner_full(self, world):
      new_entity = MinerNotFull(self.name, self.resource_limit, self.position, self.rate, self.imgs, self.animation_rate)
      return new_entity


# This is a less than pleasant file format, but structured based on
# material covered in course.  Something like JSON would be a
# significant improvement.

