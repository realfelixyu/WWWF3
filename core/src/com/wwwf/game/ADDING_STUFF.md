# Guide to Adding Stuff
## Client-Side
Never directly modify things in the world or server,
use commands instead. You can access thing in the world.
### Adding New Textures
Suppose we want to add textures to elephant unit type. In AnimationLoader.loadAnimations() add.
```
typeToAnimations.put(Entity.Type.ELEPHANT, loadAnimationsFromSheet("animations/elephant")
```
Add   
* elephant_piv.png (pivot points)
* elephant_cl.png (color layer)
* elephant.png (base texture)
* elephant.json (metadata)
## Server-Side
If you want to add features to entity try to use components. \
To make a component: 
1. Make a class that implements Component 
2. Only influence other components using messages, which can be sent by storing a pointer to the entity (master) this component is attached to and calling master.dispatcher.dispatchMessage(). 
3. Create update and free methods 
4. Have teleCodes return an array of all the messages this component receives
## Notes
* Use Gdx.app.log() for error messaging type things.
* Put handleMessage() function at top of class so we can easily see which messages it takes in