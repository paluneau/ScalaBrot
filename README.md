# ScalaBrot

A short Scala/Java program rendering the Mandlebrot Set in b&amp;w. 

Inspired by [this Haskell project](https://github.com/cies/haskell-fractal).

The program uses Scala to do all the processing of the Mandlebrot sequence values, and it uses [Java AWT BufferedImage](https://docs.oracle.com/javase/7/docs/api/java/awt/image/BufferedImage.html) to produce the output as a PNG (could be changed to bitmap or to another format).

The code was developped in [Scala IDE plugin for Eclipse](http://scala-ide.org/). This is kind of an experiment to me, as I am relatively new to functional languages. I plan on eventually adding support for Julia Sets as well, and maybe to render some "zoom in" videos.

## Customize Output Image Size & Zoom

By tweaking the constants in the file you can modify the output image. Width and Height are the image's resolution (in pixels). The Max/Min are the position (x or y) of the limits of the zoom. Watch out for the aspect ratio when you modify the x-y ranges, you might end up with a flattened or stretched image. You can also choose the number of iterations that the Mandlebrot Formula will execute over itself. 
