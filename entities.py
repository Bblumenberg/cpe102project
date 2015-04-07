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
