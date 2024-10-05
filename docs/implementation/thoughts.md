# Thoughts that come out while devving

## Main

Main should do the following:
- create the view with the for yield and save it into a State[Window, Unit]
- pass this state to all controllers in order to let those controllers add their view
- in the end show the view
- create the event stream from the view
- pass this stream to all controllers in order to let those controllers add their handlers
- close the for yield statement and then run the application (run, method implemented in the State class)

the final code will look like this:

```scala
val windowCreation = for 
    ui <- setSize(300, 300) //returns a State[Window, Unit]
    _ <- controller1.setupUI(ui)
    _ <- controller2.setupUI(ui)
    _ <- controller3.setupUI(ui)
    ...
    _ <- show()
    e <- eventStream() //returns a State[Window, Stream[UIEvent]]
    _ <- controller1.setupEventHandling(e)
    _ <- controller2.setupEventHandling(e)
    _ <- controller3.setupEventHandling(e)
    ...
yield ()

windowCreation.run(initialWindow)
```