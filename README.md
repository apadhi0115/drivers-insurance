# drivers-insurance

This application has three APIs:

/driver/create- A POST end point allows new drivers to be created and stored 
/drivers -- A GET endpoint which returns a list of all existing drivers in JSON format
/drivers/byDate?date=<date> -- A GET endpoint which returns a list of all drivers created after the specified date.
  
  The json data are stored in a flat file
  
 ** How to build the application: **
  
  git clone git@github.com:apadhi0115/drivers-insurance.git
  cd drivers-insurance
  ./mvnw spring-boot:package
  ./mvnw spring-boot:run
  
**How to Test in local environment**
	
	http://localhost:8080/drivers -- GET- should return response like below or [] if no data present
	
	[
    {
        "id": "00057d29-9ec4-44d7-9f54-44129970e8e4",
        "firstname": "Kudung Kudung",
        "lastname": "Padhiiiiiii",
        "date_of_birth": "2015-12-16",
        "creation_date": "2021-12-24T00:26:19.736558"
    },
    {
        "id": "52db7ba2-3d08-4aba-99ca-163a7849d2bb",
        "firstname": "kyun kyun",
        "lastname": "Padhiiiiiii",
        "date_of_birth": "2015-12-16",
        "creation_date": "2021-12-24T01:05:58.605533"
    }
]
	
	http://localhost:8080/driver/create --POST- Should take input body as below
	{
    "firstname": "hudu hudu",
    "lastname": "Padhiiiiiii",
    "date_of_birth": "2012-12-11"
}
	
	and it should return response as below : 
	{
    "id": "6d6d8757-8f7d-484e-a7dc-ff9356fa47b0",
    "firstname": "hudu hudu",
    "lastname": "Padhiiiiiii",
    "date_of_birth": "2012-12-11",
    "creation_date": "2021-12-24T03:20:39.215012"
}
	
	The ID and creation_date are created by the application before saving the data to the file
	
	http://localhost:8080/drivers/byDate?date=2021-12-22 --GET - should return the drivers created after the specified date
	
	[
    {
        "id": "00057d29-9ec4-44d7-9f54-44129970e8e4",
        "firstname": "Kudung Kudung",
        "lastname": "Padhiiiiiii",
        "date_of_birth": "2015-12-16",
        "creation_date": "2021-12-24T00:26:19.736558"
    },
    {
        "id": "52db7ba2-3d08-4aba-99ca-163a7849d2bb",
        "firstname": "kyun kyun",
        "lastname": "Padhiiiiiii",
        "date_of_birth": "2015-12-16",
        "creation_date": "2021-12-24T01:05:58.605533"
    },
    {
        "id": "6d6d8757-8f7d-484e-a7dc-ff9356fa47b0",
        "firstname": "hudu hudu",
        "lastname": "Padhiiiiiii",
        "date_of_birth": "2012-12-11",
        "creation_date": "2021-12-24T03:20:39.215012"
    }
]
	
**	Validations:**
	Wrong date format in the input should give response like below with error code 400 bad request
	
	{
    "errorList": {
        "date_of_birth": "Date of birth should be yyyy-MM-dd"
    }
}
	
  
