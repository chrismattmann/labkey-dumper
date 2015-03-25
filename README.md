# Labkey Dumper

This project builds upon the [Labkey Client](https://github.com/chrismattmann/labkey-client) program to connect to a Labkey instance and then to dump out the Study objects from it into a JSON format. The JSON format looks like:

```
{"studies":[
   {
     "ParticipantAliasSourceProperty" : "null",
     "ParticipantAliasProperty" : "null",
     "Description" : "Some Description",
     "EndDate" : "null",
     "_labkeyurl_Label" : "/labkey/project/Project Name/start.view?",
     "AssayPlan" : "null",
     "Grant" : "Some Foundation",
     "StartDate" : "Tue Jan 01 00:00:00 EST 2008",
     "Investigator" : "Some PI, PhD",
     "ParticipantAliasDatasetId" : "null",
     "Label" : "Interactive Example - Study",
     "Species" : "null",
     "Container" : "6f743c1b-9506-1032-b9bf-51af32c4d069",
     "_labkeyurl_Container" : "/labkey/project/Study Name/begin.view?",
   }
    ]
}

```

# Building this appilcation

0. mkdir -p $HOME/src && cd $HOME/src
1. git clone https://github.com/chrismattmann/labkey-client.git
2. cd labkey-client && mvn install assembly:assembly
3. cd ..
4. git clone https://github.com/chrismattmann/labkey-dumper.git
5. cd labkey-dumper && mvn install assembly:assembly

All done!

# Running this application

A sample command would be:

```
java -Djava.ext.dirs=target gov.nasa.jpl.celgene.labkey.LabkeyDumper --url labkey-url --user email--pass thepass --project "Default Project"

```