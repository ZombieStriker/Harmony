# Harmony
Harmony is a cross-plugin framework to allow for easier communication across multiple plugins. This is done by creating pre-made structures that plugins can use to register data or states to. This plugin also comes with a built-in resourcepack creator, for plugins that wish to add their own custom content.

# How to use:
TODO: Get Maven set up.

All the public methods can be found in the Harmony.class file.


## Custom Events:

| Class                       | Description                                                        | Use                                                                                                                         |
|-----------------------------|--------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------|
| PrepareCustomItemLoadEvent  | Called when the plugin is attempting to load all the custom items. | For loading your custom items                                                                                               |
| CustomItemLoadCompleteEvent | Called once all the custom items have already been loaded.         | For getting which spots are open for new custom items<br/> Registering new custom items,<br/>Iterating through custom items |

# TODO:
* Add Custom Block Support
* Add Custom Entity Support
* Add More frameworks