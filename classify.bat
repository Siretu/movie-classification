call mallet import-dir --input subtitles\genres\* --output testruns\movies.vectors
call mallet train-classifier --input testruns\movies.vectors --trainer NaiveBayes --training-portion 1 --output-classifier testruns\movies.classifier
call mallet classify-dir --input subtitles\unsorted --output testruns\classification_results.txt --classifier testruns\movies.classifier