# Movie Recommendation System

## Team Members
- Wenny Deng: wedeng
- Ebrahim Mohamud: EbrahimMohamud
- Nwabuife Amede: Buife-C
- Samy Bouarroudj: mysticdyingwhale

## User Stories

### User Story 1
**Assigned to:** Nwabuife Amede
**Description:** As a user, I want to search for a movie and add it to my "To-Watch" list so I can find it later.

**Use Cases:**
- Search Movies: User enters search query → System searches TMDB API → Returns movie results
- Add to Watch List: User selects movie → System adds to user's watch list → Confirms addition

**User Interactions:**
- MovieSearchController + MovieSearchPresenter
- AddToWatchListController + AddToWatchListPresenter

### User Story 2
**Assigned to:** []
**Description:** ...

**Use Cases:**
...

**User Interactions:**
...

### User Story 3
**Assigned to:** []
**Description:** ...

**Use Cases:**
...

**User Interactions:**
...

### User Story 4
**Assigned to:** []
**Description:** ...

**Use Cases:**
...

**User Interactions:**
...

### User Story 5
**Assigned to:** []
**Description:** ...

**Use Cases:**
...

**User Interactions:**
...

### User Story 6
**Assigned to:** []
**Description:** ...

**Use Cases:**
...

**User Interactions:**
...

## Entities
- **AppUser**: Stores user data including tmdbAccountID, username, ratedMovies, preferredGenres, savedMovies (used as watch list)
- **Movie**: Represents movie data from TMDB API

## Note Application

This is a minimal example demonstrating usage of the
password-protected user part of the API used in lab 5.

You can find more information about the API endpoints in
[the documentation](https://www.postman.com/cloudy-astronaut-813156/csc207-grade-apis-demo/documentation/fg3zkjm/5-password-protected-user).

If your team is considering an application for which it would be convenient to
store data in something like a database, you may find that the API calls demonstrated
here will be useful in your project, as this will allow you to store
an arbitrary JSON object associated with a username and password.

In this application, a single note has a name (the "username" in terms of the API) and the note
can be read by anyone who knows the name — but only edited by someone who
knows the password for it.

You can see the documentation in the various files for more information.

## Testing

The repo also includes an example of a use case interactor test, as well as
an example of an end-to-end test which automates button clicks and inspects
the contents of the actual views. This is something we discussed in the lectures
about testing in CA but had not provided a code example of before. Note, one
could also inspect the contents of the ViewModel objects instead when testing
CA to make a similar test which would be less dependent on the details of the
specific UI implementation.

## Project Starter Code

Your team may choose to use this repo as starter code for your project. You could
also use the lab 5 code — or start from an empty repo if your team prefers.

If you choose to use one of the repositories we have provided, you can either make
a fork of it or copy the subset of code you want into a completely new repository.
