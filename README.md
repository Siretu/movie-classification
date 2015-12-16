Movie Classifier using subtitles

~~Uses the JSAT libary availble at: https://github.com/EdwardRaff/JSAT~~ Not anymore

To use Mallet:
  http://mallet.cs.umass.edu/classification.php
Mallet repo:
  https://github.com/mimno/Mallet

Copmile mallet by running ant

To import data:
    bin\mallet import-dir --input subtitles\genres\* --output movies.vectors
To build classifier:
    bin\mallet train-classifier --input movies.vectors --output-classifier movies.classifier
To try the classifier on new data: 
    bin\mallet classify-dir --input subtitles\unsorted --output output.txt --classifier movies.classifier
