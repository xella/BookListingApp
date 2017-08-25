# BookListingApp

For this project, I had to design and create the structure of a Book Listing app which allows a user to get a list of published books on a given topic.

I used the google books API in order to fetch results and display them to the user.

App contains a ListView which becomes populated with list items.

Upon device rotation, the app saves state and restore the list back to the previously scrolled position.

The user can enter a word or phrase to serve as a search query. 

The app fetches book data related to the query via an HTTP request from the Google Books API.

The app checks whether the device is connected to the internet and responds appropriately.

When there is no data to display, the app shows a default TextView that informs the user how to populate the list.

