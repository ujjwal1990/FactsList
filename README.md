# FactsList


    /* *******Technical specification of the app ************
    *
      * 
    * ------------Libraries used in the project-----------------
    * 1> Retrofit (For Networking call)
    * 2> Glide for Image loading and caching of images
    * 3> JUnit4 Unit test cases
    * 4> Mockito for Mocking the classes for Unit Testing
    * 5> Data Binding
     *
     *  ----------Project architecture and design pattern------------------
     * 1> This project is based on MVVM (Model view view model), and we are using
     * Data binding for updating the views data from  view model.
     * 2> I have used SingleTon, Factory design pattern also on different places
     *
     * -----------Others----------------
     * Pull to refresh is enabled in the app
     * I am using Fragments, Toolbar, Material design styles in app.
     * Orientation change is  handled in the app.
     * Network check is  available in the app to make the api call.
     * App will show a network error and ask for pull to refresh if network is not available. 
     * If user will enable the network and try pull to refresh the data will be updated in tje recyclerview.
     * */
