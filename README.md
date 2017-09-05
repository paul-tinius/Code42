# Organization Tool

## Assumptions
    1. Static input data file names ( test-data/small-org-data-file.csv and test-data/small-user-data-file.csv ), it would be easy to extend to allow command line options.
    2. Static output data file ( test-data/organizations-output.txt )

## Limitations

    1. Limited unit test coverage
 
### Questions 
Why did you use the algorithms? What alternatives were there?

```
Could have used
    - binary search to store the data in memory
    - TreeMap instead of a Set
```

If there where 500 million Organizations how would you change your implementation?

```
The in-memory store would be replaced by ( database either SQL or NoSql ) that allow for better storage management 
but is still performent. Maybe Redis, allow fast access and can be used as cache.

Other enhancements:
    - Abstract out the store.
    - Add command line processesing, so that different data files can be provided as run time.
    - Utilize open-source/third party libraries ( better testing, time tested and proven )
```

## Compile
To compile the utility:

`./gradlew clean build`

## Run
To run the utility:

`./runit.sh`

### License
Copyright (c) 2017 Paul E. Tinius

This library is licensed under the Apache License, Version 2.0.

See http://www.apache.org/licenses/LICENSE-2.0.html or the LICENSE file in this repository for the full license text.