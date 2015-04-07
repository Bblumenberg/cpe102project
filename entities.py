import point
import actions
import worldmodel

class Background:
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

class MinerNotFull:
   def __init__(self, name, resource_limit, position, rate, imgs,
      animation_rate):
      self.name = name
      self.position = position
      self.rate = rate
      self.imgs = imgs
      self.current_img = 0
      self.resource_limit = resource_limit
      self.resource_count = 0
      self.animation_rate = animation_rate
      self.pending_actions = []
   def set_position(self, point):
      self.position = point
   def get_position(self):
      return self.position
   def get_images(self):
      return self.imgs
   def get_image(self):
      return self.imgs[self.current_img]
   def get_rate(self):
      return self.rate
   def set_resource_count(self, n):
      self.resource_count = n
   def get_resource_count(self):
      return self.resource_count
   def get_resource_limit(self):
      return self.resource_limit
   def get_name(self):
      return self.name
   def get_animation_rate(self):
      return self.animation_rate
   def remove_pending_action(self, action):
      self.pending_actions.remove(action)
   def add_pending_action(self, action):
      self.pending_actions.append(action)
   def get_pending_actions(self):
      return self.pending_actions
   def clear_pending_actions(self):
      self.pending_actions = []
   def next_image(self):
      self.current_img = (self.current_img + 1) % len(self.imgs)
   def entity_string(self):
      return ' '.join(['miner', self.name, str(self.position.x),
         str(self.position.y), str(self.resource_limit),
         str(self.rate), str(self.animation_rate)])
   def miner_to_ore(self, world, ore):
      if not ore:
         return ([self.position], False)
      ore_pt = ore.get_position()
      if self.position.adjacent(ore_pt):
         self.resource_count += 1
         actions.remove_entity(world, ore)
         return ([ore_pt], True)
      else:
         new_pt = actions.next_position(world, self.position, ore_pt)
         return (worldmodel.move_entity(world, self, new_pt), False)
   def create_miner_action(self, world, i_store):
      def action(current_ticks):
         self.remove_pending_action(action)
         ore = worldmodel.find_nearest(world, self.position, Ore)
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

class MinerFull:
   def __init__(self, name, resource_limit, position, rate, imgs,
      animation_rate):
      self.name = name
      self.position = position
      self.rate = rate
      self.imgs = imgs
      self.current_img = 0
      self.resource_limit = resource_limit
      self.resource_count = resource_limit
      self.animation_rate = animation_rate
      self.pending_actions = []
   def set_position(self, point):
      self.position = point
   def get_position(self):
      return self.position
   def get_images(self):
      return self.imgs
   def get_image(self):
      return self.imgs[self.current_img]
   def get_rate(self):
      return self.rate
   def set_resource_count(self, n):
      self.resource_count = n
   def get_resource_count(self):
      return self.resource_count
   def get_resource_limit(self):
      return self.resource_limit
   def get_name(self):
      return self.name
   def get_animation_rate(self):
      return self.animation_rate
   def remove_pending_action(self, action):
      self.pending_actions.remove(action)
   def add_pending_action(self, action):
      self.pending_actions.append(action)
   def get_pending_actions(self):
      return self.pending_actions
   def clear_pending_actions(self):
      self.pending_actions = []
   def next_image(self):
      self.current_img = (self.current_img + 1) % len(self.imgs)
   def miner_to_smith(self, world, smith):
      if not smith:
         return ([self.position], False)
      smith_pt = smith.get_position()
      if self.position.adjacent(smith_pt):
         smith.set_resource_count(smith.get_resource_count() + self.resource_count)
         self.resource_count = 0
         return ([], True)
      else:
         new_pt = actions.next_position(world, self.position, smith_pt)
         return (worldmodel.move_entity(world, self, new_pt), False)
   def create_miner_action(self, world, i_store):
      def action(current_ticks):
         self.remove_pending_action(action)
         smith = worldmodel.find_nearest(world, self.position, Blacksmith)
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

class Vein:
   def __init__(self, name, rate, position, imgs, resource_distance=1):
      self.name = name
      self.position = position
      self.rate = rate
      self.imgs = imgs
      self.current_img = 0
      self.resource_distance = resource_distance
      self.pending_actions = []
   def set_position(self, point):
      self.position = point
   def get_position(self):
      return self.position
   def get_images(self):
      return self.imgs
   def get_image(self):
      return self.imgs[self.current_img]
   def get_rate(self):
      return self.rate
   def get_resource_distance(self):
      return self.resource_distance
   def get_name(self):
      return self.name
   def remove_pending_action(self, action):
      self.pending_actions.remove(action)
   def add_pending_action(self, action):
      self.pending_actions.append(action)
   def get_pending_actions(self):
      return self.pending_actions
   def clear_pending_actions(self):
      self.pending_actions = []
   def next_image(self):
      self.current_img = (self.current_img + 1) % len(self.imgs)
   def entity_string(self):
      return ' '.join(['vein', self.name, str(self.position.x),
         str(self.position.y), str(self.rate),
         str(self.resource_distance)])

class Ore:
   def __init__(self, name, position, imgs, rate=5000):
      self.name = name
      self.position = position
      self.imgs = imgs
      self.current_img = 0
      self.rate = rate
      self.pending_actions = []
   def set_position(self, point):
      self.position = point
   def get_position(self):
      return self.position
   def get_images(self):
      return self.imgs
   def get_image(self):
      return self.imgs[self.current_img]
   def get_rate(self):
      return self.rate
   def get_name(self):
      return self.name
   def remove_pending_action(self, action):
      self.pending_actions.remove(action)
   def add_pending_action(self, action):
      self.pending_actions.append(action)
   def get_pending_actions(self):
      return self.pending_actions
   def clear_pending_actions(self):
      self.pending_actions = []
   def next_image(self):
      self.current_img = (self.current_img + 1) % len(self.imgs)
   def entity_string(self):
      return ' '.join(['ore', self.name, str(self.position.x),
         str(self.position.y), str(self.rate)])

class Blacksmith:
   def __init__(self, name, position, imgs, resource_limit, rate,
      resource_distance=1):
      self.name = name
      self.position = position
      self.imgs = imgs
      self.current_img = 0
      self.resource_limit = resource_limit
      self.resource_count = 0
      self.rate = rate
      self.resource_distance = resource_distance
      self.pending_actions = []
   def set_position(self, point):
      self.position = point
   def get_position(self):
      return self.position
   def get_images(self):
      return self.imgs
   def get_image(self):
      return self.imgs[self.current_img]
   def get_rate(self):
      return self.rate
   def set_resource_count(self, n):
      self.resource_count = n
   def get_resource_count(self):
      return self.resource_count
   def get_resource_limit(self):
      return self.resource_limit
   def get_resource_distance(self):
      return self.resource_distance
   def get_name(self):
      return self.name
   def remove_pending_action(self, action):
      self.pending_actions.remove(action)
   def add_pending_action(self, action):
      self.pending_actions.append(action)
   def get_pending_actions(self):
      return self.pending_actions
   def clear_pending_actions(self):
      self.pending_actions = []
   def next_image(self):
      self.current_img = (self.current_img + 1) % len(self.imgs)
   def entity_string(self):
      return ' '.join(['blacksmith', self.name, str(self.position.x),
         str(self.position.y), str(self.resource_limit),
         str(self.rate), str(self.resource_distance)])

class Obstacle:
   def __init__(self, name, position, imgs):
      self.name = name
      self.position = position
      self.imgs = imgs
      self.current_img = 0
   def set_position(self, point):
      self.position = point
   def get_position(self):
      return self.position
   def get_images(self):
      return self.imgs
   def get_image(self):
      return self.imgs[self.current_img]
   def get_name(self):
      return self.name
   def next_image(self):
      self.current_img = (self.current_img + 1) % len(self.imgs)
   def entity_string(self):
      return ' '.join(['obstacle', self.name, str(self.position.x),
         str(self.position.y)])

class OreBlob:
   def __init__(self, name, position, rate, imgs, animation_rate):
      self.name = name
      self.position = position
      self.rate = rate
      self.imgs = imgs
      self.current_img = 0
      self.animation_rate = animation_rate
      self.pending_actions = []
   def set_position(self, point):
      self.position = point
   def get_position(self):
      return self.position
   def get_images(self):
      return self.imgs
   def get_image(self):
      return self.imgs[self.current_img]
   def get_rate(self):
      return self.rate
   def get_name(self):
      return self.name
   def get_animation_rate(self):
      return self.animation_rate
   def remove_pending_action(self, action):
      self.pending_actions.remove(action)
   def add_pending_action(self, action):
      self.pending_actions.append(action)
   def get_pending_actions(self):
      return self.pending_actions
   def clear_pending_actions(self):
      self.pending_actions = []
   def next_image(self):
      self.current_img = (self.current_img + 1) % len(self.imgs)
   def blob_next_position(self, world, dest_pt):
      horiz = actions.sign(dest_pt.x - self.position.x)
      new_pt = point.Point(self.position.x + horiz, self.position.y)
      if horiz == 0 or (worldmodel.is_occupied(world, new_pt) and not isinstance(worldmodel.get_tile_occupant(world, new_pt), Ore)):
         vert = actions.sign(dest_pt.y - self.position.y)
         new_pt = point.Point(self.position.x, self.position.y + vert)
         if vert == 0 or (worldmodel.is_occupied(world, new_pt) and not isinstance(worldmodel.get_tile_occupant(world, new_pt), Ore)):
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
         old_entity = worldmodel.get_tile_occupant(world, new_pt)
         if isinstance(old_entity, Ore):
            actions.remove_entity(world, old_entity)
         return (worldmodel.move_entity(world, self, new_pt), False)
   def create_action(self, world, i_store):
      def action(current_ticks):
         self.remove_pending_action(action)
         vein = worldmodel.find_nearest(world, self.position, Vein)
         (tiles, found) = self.blob_to_vein(world, vein)
         next_time = current_ticks + self.rate
         if found:
            quake = actions.create_quake(world, tiles[0], current_ticks, i_store)
            worldmodel.add_entity(world, quake)
            next_time = current_ticks + self.rate * 2
         actions.schedule_action(world, self, self.create_action(world, i_store), next_time)
         return tiles
      return action
   def schedule_blob(self, world, ticks, i_store):
      actions.schedule_action(world, self, self.create_action(world, i_store), ticks + self.rate)
      actions.schedule_animation(world, self)

class Quake:
   def __init__(self, name, position, imgs, animation_rate):
      self.name = name
      self.position = position
      self.imgs = imgs
      self.current_img = 0
      self.animation_rate = animation_rate
      self.pending_actions = []
   def set_position(self, point):
      self.position = point
   def get_position(self):
      return self.position
   def get_images(self):
      return self.imgs
   def get_image(self):
      return self.imgs[self.current_img]
   def get_name(self):
      return self.name
   def get_animation_rate(self):
      return self.animation_rate
   def remove_pending_action(self, action):
      self.pending_actions.remove(action)
   def add_pending_action(self, action):
      self.pending_actions.append(action)
   def get_pending_actions(self):
      return self.pending_actions
   def clear_pending_actions(self):
      self.pending_actions = []
   def next_image(self):
      self.current_img = (self.current_img + 1) % len(self.imgs)


# This is a less than pleasant file format, but structured based on
# material covered in course.  Something like JSON would be a
# significant improvement.

