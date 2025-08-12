## Principle of Universal Design

### Single Responsibility Principle
- Each class has only one reason to change. 
- For example, MovieSearchService.java only has interface methods related to movie searching, and SearchInputBoundary.java interface only has methods for executing the search.


### Open Closed Principle
- Open for extension, closed for modification.
- We are able to add more search filters without modifing SearchInteractor

### Liskov Substitution Principle
- We use superclass reference variables so that subtypes can substitute for base types.
- For example, any SearchOutputBoundary implementation works with the interactor

### Interface Segregation Principle
- We adhere to this principle by having lots of small, specific interfaces than fewer larger ones, so that the design is easier to be extended and modified.
- For each use case, we have defined multiple interfaces for input/output boundary, and service.

### Dependency Inversion Principle
- Both high-level and low-level modules depend on abstractions.
- All interactor classes depend on interfaces, for example, SearchInteractor depends on MovieFieldRegisterInterface, SearchOutputBoundary, and MovieDataAccessInterface.

## Who to Market our Program towards?
We can sell or license it mainly to movie enthusiasts, who actively track new releases, explore genres, and maintain watch lists. This would be our core market.
In addition to it, people who watch movies occasionally but want better discovery may also be interested in it.

## For Certain Demographics
- Non-English speakers, since UI is in English and lack of multilingual support, and limited movies are covered for non-English TMDB movie results.
- Users with accessibility needs, features like screen reader support, high-contrast mode are yet to implement.
