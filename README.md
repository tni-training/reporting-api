Test Http Rest Point Using curl

request for getting all jobs:
    $ curl localhost:8081/alljobs

request for getting job with id:
    $  curl http://localhost:8081/job\?id\=35

request for add new job:
    $ curl -d '{"action":"NewJob", "submissionId":"138", "message":"Thirty Eight","jarParams":"Word", "serverSparkVersion":"2.12","isAccepted":2, "status":"process", "isCompleted":2}' -H "Content-Type: application/json" -X POST http://localhost:8081/addjob 

request for update the job:
    
$ curl -XPUT 'http://localhost:8081/updatejob' -d '{"action":"Job41", "submissionId":"138", "message":"Thirty Eight","jarParams":"Word", "serverSparkVersion":"2.12", "isAccepted":2, "status":"process", "isCompleted":2, "id":34

    If don't pass the job id it will tell you to do so, if u pass the wrong job id it will tell job id is not present

request for deleteing a job with id:
    $ curl -XDELETE http://localhost:8081/removejob\?id\=42

