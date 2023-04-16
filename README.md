# Image Rendering

An ongoing project of a graphical engine for generating moving objects in 3D using the Phong reflection model by ray casting and ray tracing techniques.

> The Phong reflection model simulates light interacting with surfaces in computer graphics, using ambient, diffuse, and specular components to calculate pixel color.

> Ray casting is a simple technique where rays are shot from the camera into the scene, and the first object that intersects with each ray determines the color of the pixel. It is a quick way to generate images, but it does not take into account reflections or shadows.

> Ray tracing is an advanced technique that simulates the behavior of light in a scene by tracing rays as they bounce off objects and interact with the environment. This allows for more realistic lighting effects such as reflections, refractions, and shadows. Ray tracing is computationally intensive, but it produces high-quality images with natural lighting.

I created a two-dimensional image in which there are various geometric shapes such as polygons, triangles, spheres and diamonds. Moving the camera around the objects produces a clip of moving objects.

<img src="https://github.com/SarelSchlesinger/ImageRendering/blob/master/images/diamondsScene.gif">
