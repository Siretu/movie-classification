Movie Classifier using subtitles

~~Uses the JSAT libary availble at: https://github.com/EdwardRaff/JSAT~~ Not anymore

To use Mallet:

http://mallet.cs.umass.edu/classification.php

Copmile mallet by running ant

To import data:
  bin\mallet import-dir --input subtitles/* --output movies.mallet
To build classifier:
  bin\mallet train-classifier --input movies.mallet --output-classifier movies.classifier
To try the classifier on new data: 
  bin\mallet classify-file --input testdata\Mad.Max.Fury.Road.2015.1080p.BluRay.x264.AC3-ETRG.English.stipped --output a --classifier movies.classifier
