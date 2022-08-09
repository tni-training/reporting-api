# Test Http Rest Point Using curl


1) Get all Jobs

request:
```
$ curl localhost:8081/alljobs
```
2) Get Job with id

request:
```
$  curl http://localhost:8081/job\?id\=35
```

3) Add new Job

request:
```
$ curl -d '{"action":"NewJob", "submissionId":"138", "message":"Thirty Eight","jarParams":"Word", "serverSparkVersion":"2.12","isAccepted":2, "status":"process", "isCompleted":2}' -H "Content-Type: application/json" -X POST http://localhost:8081/addjob
```

4) Update Jobs 

request:

```
$ curl -XPUT 'http://localhost:8081/updatejob' -d '{"action":"Job41", "submissionId":"138", "message":"Thirty Eight","jarParams":"Word", "serverSparkVersion":"2.12", "isAccepted":2, "status":"process", "isCompleted":2, "id":34
```
If don't pass the job id it will tell you to do so, if u pass the wrong job id it will tell job id is not present

5) Delete Job with ids

##### For Deleting a Single Job

request:
```
$ curl -XDELETE http://localhost:8081/removejob\?ids\=42
```

##### For Deleting Multiple Job

```
$ curl -XDELETE http://localhost:8081/removejob\?ids\=58,61,47

```
