---
geometry: margin=1in
---
# PROJECT Design Documentation
​
> _The following template provides the headings for your Design
> Documentation.  As you edit each section make sure you remove these
> commentary 'blockquotes'; the lines that start with a > character
> and appear in the generated PDF in italics._
​
## Team Information
### Team name: Scrumblebees
![Team logo](https://i.imgur.com/6moQ15s.png)
* Team members
  * Ryan Webb
  * Kanisha Agrawal
  * Noah Landis
  * Priyank Patel
​
## Executive Summary
​
Introducing Wizbiz (development codename), a captivating web application that transports the wonderful world of magical commerce into the digital age. Wizbiz offers users a spellbinding platform to explore and acquire a wide array of magical items, from wands to brooms, and everything in between.

In its present iteration, Wizbiz allows users to effortlessly create an account, peruse the diverse selection of magical items, and add desired products to their cart. Moreover, the application's owner possesses the ability to enrich the store's inventory by adding new items, as well as editing or deleting existing offerings as needed.

Built using Angular and TypeScript for the front-end, our eStore is easy to navigate and visually appealing, thanks to the Angular Material UI component library. Customers can browse through our collection of magical items effortlessly and enjoy a smooth shopping experience.

The back-end, powered by a Java Spring Boot API, takes care of essential elements like carts, users, and inventory management. This ensures a secure and reliable platform for our customers to shop with confidence.
​
### Purpose
>  _**[Sprint 2 & 4]** Provide a very brief statement about the project and the most
> important user group and user goals._
​
Wizbiz provides a platform for users to search and buy magic products. The most important user group for this website are people who want to buy magical items. The primary user goals for this project are to easily be able to find and purchase magic products online.
### Glossary and Acronyms
> _**[Sprint 2 & 4]** Provide a table of terms and acronyms._
​
| Term |        Definition       |
|------|-------------------------|
| SPA  | Single Page Application |
| API  | Application Programming Interface |
| DAO  | Data Access Object |
| SKU  | Stock Keeping Unit |
| UI   | User Interface |
| MVC  | Model-View-Controller |
| MVVM | Model-View-ViewModel |
| CRUD | Create, Read, Update, Delete |
| HTTP | Hypertext Transfer Protocol |
| REST | Representational State Transfer |
​
## Requirements
​
This section describes the features of the application.
​
> _In this section you do not need to be exhaustive and list every
> story.  Focus on top-level features from the Vision document and
> maybe Epics and critical Stories._
​
1)There should be simple authentication system for both the admin and the users.
2)Users should be able to create and account and login/logout from the website.
3)Users should be able to see a list of products in the webiste and also be able to search for the products they need.
4)Users should have full control of the items in cart and their quantities.
5)All the data of the users should be saved to the inventory so that users can view what's in their cart when they login next time.

### Definition of MVP
> _**[Sprint 2 & 4]** Provide a simple description of the Minimum Viable Product._
​
A simple magic shop website that allows users to search, select, add to cart and order magic products that are in stock. The Owner of the website can manage the product by adding,deleting or editing the products displayed in the website.  
### MVP Features
>  _**[Sprint 4]** Provide a list of top-level Epics and/or Stories of the MVP._
​
### Enhancements
> _**[Sprint 4]** Describe what enhancements you have implemented for the project._
​
​
## Application Domain
​
This section describes the application domain.
​
![Domain Model](domain-model-placeholder.png)
​
> _**[Sprint 2 & 4]** Provide a high-level overview of the domain for this application. You
> can discuss the more important domain entities and their relationship
> to each other._
​
​
## Architecture and Design
​
This section describes the application architecture.
​
### Summary
​
The following Tiers/Layers model shows a high-level view of the webapp's architecture.
​
![The Tiers & Layers of the Architecture](architecture-tiers-and-layers.png)
​
The e-store web application, is built using the Model–View–ViewModel (MVVM) architecture pattern. 
​
The Model stores the application data objects including any functionality to provide persistance. 
​
The View is the client-side SPA built with Angular utilizing HTML, CSS and TypeScript. The ViewModel provides RESTful APIs to the client (View) as well as any logic required to manipulate the data objects from the Model.
​
Both the ViewModel and Model are built using Java and Spring Framework. Details of the components within these tiers are supplied below.
​
​
### Overview of User Interface
​
This section describes the web interface flow; this is how the user views and interacts
with the e-store application.
​
> _Provide a summary of the application's user interface.  Describe, from
> the user's perspective, the flow of the pages in the web application._
​
​
### View Tier
> _**[Sprint 4]** Provide a summary of the View Tier UI of your architecture.
> Describe the types of components in the tier and describe their
> responsibilities.  This should be a narrative description, i.e. it has
> a flow or "story line" that the reader can follow._
​
> _**[Sprint 4]** You must  provide at least **2 sequence diagrams** as is relevant to a particular aspects 
> of the design that you are describing.  For example, in e-store you might create a 
> sequence diagram of a customer searching for an item and adding to their cart. 
> As these can span multiple tiers, be sure to include an relevant HTTP requests from the client-side to the server-side 
> to help illustrate the end-to-end flow._
​
> _**[Sprint 4]** To adequately show your system, you will need to present the **class diagrams** where relevant in your design. Some additional tips:_
 >* _Class diagrams only apply to the **ViewModel** and **Model** Tier_
>* _A single class diagram of the entire system will not be effective. You may start with one, but will be need to break it down into smaller sections to account for requirements of each of the Tier static models below._
 >* _Correct labeling of relationships with proper notation for the relationship type, multiplicities, and navigation information will be important._
 >* _Include other details such as attributes and method signatures that you think are needed to support the level of detail in your discussion._
​
### ViewModel Tier
> _**[Sprint 4]** Provide a summary of this tier of your architecture. This
> section will follow the same instructions that are given for the View
> Tier above._
​
> _At appropriate places as part of this narrative provide **one** or more updated and **properly labeled**
> static models (UML class diagrams) with some details such as critical attributes and methods._
> 
![Replace with your ViewModel Tier class diagram 1, etc.](model-placeholder.png)
​
### Model Tier
Our model tier (`M` in `MVC`) is built using Java and Spring Framework. The model tier is responsible for storing the application data objects and providing persistance. The model tier also exposes a set of APIs to the controller tier (`C` in `MVC`) to manipulate the data objects from the Model. The model tier is divided into three resources: `Inventory`, `User` and `Carts`. 

The `Inventory` resource is responsible for storing the `Product` data objects and providing persistence via Data Access Object (DAO) classes. The `Product` class is the highest abstraction class used for storing data about a product, such as name, price, SKU (Stock Keeping Unit), images, as well an instace of a `Description` object and a `Stock` object. The `Description` class is used for storing data about a product's description such as the product's nsummary text, the product's tags. `Description` also encapsulates behavior related to said data. The `Stock` class is used for storing the product's stock quantity, and encapsulates behavior related to it.

Persistence is provided by the `InventoryDAO` interface, and its concrete implementation `InventoryFileDAO`. The `ProductFileDAO` class is the layer between the product data objects and thee controller tier, and provides methods for saving, loading, and altering `Product` data objects. It achieves this via serialization and deserialization of `Product` objects to and from a JSON file.

The `User` resource is responsible for storing the `User` data objects and providing persistance. The primary data object stored in the `User` resource is the `User` class stores a user's ID, login state, and whether the user is an admin or not. The `User` class also encapsulates behavior related to said data.

Persistence is provided by the `UsersDAO` interface, and its concrete implementation `UsersFileDAO`. The `UsersFileDAO` class is the layer between the user data objects and thee controller tier, and provides methods for saving, loading, and altering `User` data objects. It achieves this via serialization and deserialization of `User` objects to and from a JSON file.

The `Carts` resource is responsible for storing the cart data objects. The primary data object stored in the `Carts` resource is the `Cart` class. The `Cart` class stores a user's ID, a map of SKUs to quantities in their cart, and an injected reference to the `InventoryDAO` singleton instance, which allows the class to calculate the total price of the cart, as well as check stock quantities before adding items to the cart. The `Cart` class also encapsulates behavior related to cart operations such as adding products, removing products, clearing the cart, and checking if the cart contains a product.

Persistence is provided by the `CartsDAO` interface, and its concrete implementation `CartsFileDAO`. The `CartsFileDAO` class is the layer between the cart data objects and thee controller tier, and provides methods for saving, loading, and altering `Cart` data objects. It achieves this via serialization and deserialization of `Cart` objects to and from a JSON file.
 
![Model tier class diagrams for backend](class-diagrams.png)
​
## OO Design Principles
> _**[Sprint 2, 3 & 4]** Discuss at least **4 key OO Principles** in your current design. This should be taken from your work in "Adherence to Architecture and Design Principles" that you have completed in a previous Sprint. Be sure to include any diagrams (or clearly refer to ones elsewhere in your Tier sections above) to support your claims._
​
> _**[Sprint 3 & 4]** OO Design Principles should span across **all tiers.**_
​
## Static Code Analysis/Future Design Improvements
> _**[Sprint 4]** With the results from the Static Code Analysis exercise, 
> **Identify 3-4** areas within your code that have been flagged by the Static Code 
> Analysis Tool (SonarQube) and provide your analysis and recommendations.  
> Include any relevant screenshot(s) with each area._
​
> _**[Sprint 4]** Discuss **future** refactoring and other design improvements your team would explore if the team had additional time._
​
## Testing
> _This section will provide information about the testing performed
> and the results of the testing._
​
### Acceptance Testing
> _**[Sprint 2 & 4]** Report on the number of user stories that have passed all their
> acceptance criteria tests, the number that have some acceptance
> criteria tests failing, and the number of user stories that
> have not had any testing yet. Highlight the issues found during
> acceptance testing and if there are any concerns._
​
### Unit Testing and Code Coverage
> _**[Sprint 4]** Discuss your unit testing strategy. Report on the code coverage
> achieved from unit testing of the code base. Discuss the team's
> coverage targets, why you selected those values, and how well your
> code coverage met your targets._
​
>_**[Sprint 2 & 4]** **Include images of your code coverage report.** If there are any anomalies, discuss
> those._
![Model tier code coverage for backend](code-coverage-model-tier.png)

