## Project-One

### Bugs

* The session may fail to be set when logging in to Google Chrome, using incognito mode fixes that.
* The Arquillian integration testing may break if run multiple times in quick succession in the same infrastructure. (This does not happen often, and has yet to happen with the testing script).

### Limitations
* The CRUD operations for the user entity are not implemented.
* Due to the lack of a datastore, accessing match data a lot will most likely destroy the server's performance, even with ressource tier paging.
This is due to a match being quite a heavy object, as it must be joined with 4 other tables to obtain.
Optimizing the underlying SQL querry may end up improving the performances, but we deemed this course of action to be outside the scope of this course.

---
[Return to the main readme](https://github.com/capito27/Teaching-HEIGVD-AMT-2019-Project-One/blob/master/README.md)
