trivago Technical Assessment
Setting
For this assessment, you will be put in the position of a Java backend developer at trivago. There
has been a decision at the
management level to re-implement the backend from scratch and see
whether the new solution can replace the legacy system
which has grown over the past several years.

Your Task
First take a look at the interface HotelSearchEngine . Your task will be to implement the two given
functions in the concrete implementation in HotelSearchEngineImpl :

initialize : this function is used to read the .csv (comma separated value) files initially.
Take a look at the
database schema section for further details.

performSearch : executes the actual search. You are given a concrete search and can use the
OfferProvider interface
to retrieve offers from the advertisers.

totalQueries : returns the number of times that performSearch has been called since the
application was started.

The initialize function will always be called only one time during startup, whereas the
performSearch function would
be called millions of times in production, often concurrently by
many threads. Make sure to do as much work as possible
in the former function so that the latter
can execute as quickly as possible, and make sure that everything called after
initialize is
completely thread-safe. Also, in a real production environment, every call to
OfferProvider.getOffersFromAdvertiser would retrieve offers from advertisers via a network
request. This is a very costly operation, so it should be called as few times as possible!

Database schema
The data for your search engine is stored in the following csv files under src/main/resources :
cities.csv : contains ten cities with a name and an id
hotel_advertiser.csv : models the m-n relationship between hotels and advertisers; every
advertiser might have
offers for m hotels and each hotel might be offered by n advertisers.
Contains two columns, referencing the ids of
advertisers and hotels
hotels : the given hotels for your search engine. Contains an id field and a foreign key to
the cities table
indicating each hotel's location. Next, there are five columns that further
describe the hotel: the number of clicks
and impressions over the last week, the name, the
user rating (0 - 100), and the hotel star rating (1 - 5).
Note that the CSV files are not perfectly formatted; some fields are surrounded by extraneous
whitespace, and you'll need to deal with this somehow.

Important Notes
What We're Looking For
The code should be clean and easy to read and understand
The initialize method should be effectively utilized to minimize CPU resources used in
performSearch . Do not worry about memory usage.
Network resources should be conserved, meaning that network calls AND the data sent in
each network call are minimized.
Additionally, network requests should be sent in parallel, NOT serially!
Everything which is called after initialize MUST be thread-safe, as the production webserver
would itself be multi-threaded.
The provided tests should pass. We're very happy to see any consideration for sorting the results returned by performSearch .

Hints
The existing unit tests in HotelSearchEngineTest should pass when you have completed your
implementation. However,
beware that they are not exhaustive (feel free to add more).
If necessary, you can extend all classes with additional methods. You can also change
existing method signatures,
except for those in Offer and OfferProvider (let's say that
they were agreed upon with an external party).
You can add any 3rd party libraries you like.
If you think of a neat feature or improvement beyond the list of requirements above, just add
some comments explaining
your ideas.

Feel free to send an e-mail if you have any questions.
Building
We have provided a Gradle build file for convenience, since we use Gradle internally. Here are
some useful gradle
commands for this project:
./gradlew build : compile and test
./gradlew clean : delete all of the build artifacts
./gradlew test : run unit tests
