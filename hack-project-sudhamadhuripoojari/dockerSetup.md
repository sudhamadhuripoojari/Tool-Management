## A few words on the Docker setup for the databases

This section is more if you want to know how the Docker stuff works internally.

In order to this to be reasonable to do in a weekend, and to only focus on the access code rather than the setup - we have created a `docker-compose.yml` file for you. This file will create the environment you will need:

* A PostGres server on port 5432
  * You can use `pgAdmin` (installed on your computers) to access the database
  * The data is stored in `./data/psql`, in the same directory as this repository. And is not checked-in...
  * restarting it with that folder mapped means that the data, e.g carts created, will remain intact between restarts. 


### Credentials and .env files

We have supplied credentials for the database in `.env` files. This is a good practice to ensure that secrets doesn't leak into the source code.
What is NOT a good practice is to check-in the `.env` files as we have done here. But that's the best way that I've found to share this with you.
`
