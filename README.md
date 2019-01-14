# BakingApp
This app contain list of recipes with detail , you can watch video and it has responsive view along with Widget which include recipe as list in HomeScreen

# Common Project Requirements
App is written solely in the Java Programming Language

App utilizes stable release versions of all libraries, Gradle, and Android Studio.

# General App Usage
App should allow navigation between individual recipes and recipe steps.

# App should display recipes from provided network resource.

# App uses RecyclerView and can handle recipe steps that include videos or images.

# Components and Libraries
Application uses Master Detail Flow to display recipe steps and navigation between them.
Application uses Exoplayer to display videos.
Application properly initializes and releases video assets when appropriate.
Application should properly retrieve media assets from the provided network links. It should properly handle network requests.
Application makes use of Espresso to test aspects of the UI.
Application sensibly utilizes a third-party library to enhance the app's features. That could be helper library to interface with ContentProviders if you choose to store the recipes, a UI binding library to avoid writing findViewById a bunch of times, or something similar.

# Homescreen Widget
Application has a companion homescreen widget.
Widget displays ingredient list for desired recipe.
