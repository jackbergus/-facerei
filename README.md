# *Facerei
## A mispelled semistructured data integration tool with "ontologies"
### Developed during the hackaton HackCortona in 2016, held in Cortona.
#### Giacomo Bergami &copy; 2016

This project has been developed with Maven. In order to run the project, you have to run the `Main` class in `it.giacomobergami.facerei`. This runs an HTTP server running on port 8000 and, by accessing to the url `localhost:8000/`, you have the following utilities:

* **Single Documents**: With this configuration the result of the query are the chunks of semistructured documents that are retrieved by the tag query
* **Integrated Documents**: With this configuration the result of the query is an aggregated verion of the previous reply, where all the semantic pieces of information are joined together through a semantic ontology. All the data in the document refer to the main entity.
* **Aggregated Documents**: This last configuration permits to aggregate the previous preliminary result aggegating the values through a taxonomy aggregation.
