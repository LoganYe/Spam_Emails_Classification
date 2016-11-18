#!/usr/bin/env python
# -*- coding: utf-8 -*-

from os import walk
import re
import numpy as np
from sklearn import linear_model, datasets, metrics
from sklearn.neural_network import BernoulliRBM
from sklearn.pipeline import Pipeline

# Read file & Create word bag
def wordbag(ham, spam):
	bag = []
	stops =["a", "about", "above", "above", "across", "after", "afterwards", "again", "against", "all", "almost", "alone", "along", "already", "also","although","always","am","among", "amongst", "amoungst", "amount",  "an", "and", "another", "any","anyhow","anyone","anything","anyway", "anywhere", "are", "around", "as",  "at", "back","be","became", "because","become","becomes", "becoming", "been", "before", "beforehand", "behind", "being", "below", "beside", "besides", "between", "beyond", "bill", "both", "bottom","but", "by", "call", "can", "cannot", "cant", "co", "con", "could", "couldnt", "cry", "de", "describe", "detail", "do", "done", "down", "due", "during", "each", "eg", "eight", "either", "eleven","else", "elsewhere", "empty", "enough", "etc", "even", "ever", "every", "everyone", "everything", "everywhere", "except", "few", "fifteen", "fify", "fill", "find", "fire", "first", "five", "for", "former", "formerly", "forty", "found", "four", "from", "front", "full", "further", "get", "give", "go", "had", "has", "hasnt", "have", "he", "hence", "her", "here", "hereafter", "hereby", "herein", "hereupon", "hers", "herself", "him", "himself", "his", "how", "however", "hundred", "ie", "if", "in", "inc", "indeed", "interest", "into", "is", "it", "its", "itself", "keep", "last", "latter", "latterly", "least", "less", "ltd", "made", "many", "may", "me", "meanwhile", "might", "mill", "mine", "more", "moreover", "most", "mostly", "move", "much", "must", "my", "myself", "name", "namely", "neither", "never", "nevertheless", "next", "nine", "no", "nobody", "none", "noone", "nor", "not", "nothing", "now", "nowhere", "of", "off", "often", "on", "once", "one", "only", "onto", "or", "other", "others", "otherwise", "our", "ours", "ourselves", "out", "over", "own","part", "per", "perhaps", "please", "put", "rather", "re", "same", "see", "seem", "seemed", "seeming", "seems", "serious", "several", "she", "should", "show", "side", "since", "sincere", "six", "sixty", "so", "some", "somehow", "someone", "something", "sometime", "sometimes", "somewhere", "still", "such", "system", "take", "ten", "than", "that", "the", "their", "them", "themselves", "then", "thence", "there", "thereafter", "thereby", "therefore", "therein", "thereupon", "these", "they", "thickv", "thin", "third", "this", "those", "though", "three", "through", "throughout", "thru", "thus", "to", "together", "too", "top", "toward", "towards", "twelve", "twenty", "two", "un", "under", "until", "up", "upon", "us", "very", "via", "was", "we", "well", "were", "what", "whatever", "when", "whence", "whenever", "where", "whereafter", "whereas", "whereby", "wherein", "whereupon", "wherever", "whether", "which", "while", "whither", "who", "whoever", "whole", "whom", "whose", "why", "will", "with", "within", "without", "would", "yet", "you", "your", "yours", "yourself", "yourselves", "the"]	
	for address in ham:
		with open("/Users/Helicopter/Desktop/Gogate_AML/Assignments/Assignment2/homework/train/ham/" + address,"r") as text_file:			
			for line in text_file:
				for word in line.split():
					letters_only = re.sub("[^a-zA-Z]", " ", word)
					word = letters_only.lower()
					if (word not in bag) and (word not in stops):
						bag.append(word)
		text_file.close()
	for address in spam:
		with open("/Users/Helicopter/Desktop/Gogate_AML/Assignments/Assignment2/homework/train/spam/" + address,"r") as text_file:			
			for line in text_file:
				for word in line.split():
					letters_only = re.sub("[^a-zA-Z]", " ", word)
					word = letters_only.lower()
					if (word not in bag) and (word not in stops):
						bag.append(word)
		text_file.close()
	return bag

# get dictionary for every email
# ham is 0, spam is 1
def get_attr(bag, ham, spam, flag):
	num_email = len(ham) + len(spam)
	num_attr = len(bag)
	X = np.zeros((num_email+1, num_attr+1))
	Y = np.zeros(num_email+1)
	i = 0
	if flag == 0:
		f = "train"
	if flag == 1:
		f = "test"
	for address in ham:
		with open("/Users/Helicopter/Desktop/Gogate_AML/Assignments/Assignment2/homework/"+f+"/ham/" + address,"r") as text_file:			
			i += 1		# index of email
			j = 0		# index of attributes
			word_dict = dict.fromkeys(bag,0)
			for line in text_file:
				for word in line.split():
					letters_only = re.sub("[^a-zA-Z]", " ", word)
					word = letters_only.lower()
					if word in bag:
						word_dict[word] += 1
		for key in word_dict:
			j += 1
			X[i, j] = word_dict[key]
		Y[i] = 0
		text_file.close()
	for address in spam:
		with open("/Users/Helicopter/Desktop/Gogate_AML/Assignments/Assignment2/homework/"+f+"/spam/" + address,"r") as text_file:			
			i += 1		# index of email
			j = 0		# index of attributes
			word_dict = dict.fromkeys(bag,0)
			for line in text_file:
				for word in line.split():
					letters_only = re.sub("[^a-zA-Z]", " ", word)
					word = letters_only.lower()
					if word in bag:
						word_dict[word] += 1
		for key in word_dict:
			j += 1
			X[i, j] = word_dict[key]
		Y[i] = 1
		text_file.close()
	return X, Y

# read file
train_ham = []
for (dirpath, dirnames, filenames) in walk("/Users/Helicopter/Desktop/Gogate_AML/Assignments/Assignment2/homework/train/ham"):
    train_ham.extend(filenames)
    break
train_spam = []
for (dirpath, dirnames, filenames) in walk("/Users/Helicopter/Desktop/Gogate_AML/Assignments/Assignment2/homework/train/spam"):
    train_spam.extend(filenames)
    break
test_ham = []
for (dirpath, dirnames, filenames) in walk("/Users/Helicopter/Desktop/Gogate_AML/Assignments/Assignment2/homework/test/ham"):
    test_ham.extend(filenames)
    break
test_spam = []
for (dirpath, dirnames, filenames) in walk("/Users/Helicopter/Desktop/Gogate_AML/Assignments/Assignment2/homework/test/spam"):
    test_spam.extend(filenames)
    break

#Main
# No of Words in bag
bag = wordbag(train_ham, train_spam)
print len(bag)
# Training set & Testing set
X_train, Y_train = get_attr(bag, train_ham, train_spam, 0)
X_test, Y_test = get_attr(bag, test_ham, test_spam, 1)
##
#print X_train
#print Y_train
#print len(X_train), len(Y_train)

####################### Training ############################

# Models we will use
logistic = linear_model.LogisticRegression()
rbm = BernoulliRBM(random_state=0, verbose=True)

classifier = Pipeline(steps=[('rbm', rbm), ('logistic', logistic)])

# Hyper-parameters. These were set by cross-validation,
# using a GridSearchCV. Here we are not performing cross-validation to
# save time.
rbm.learning_rate = 0.06
rbm.n_iter = 20
# More components tend to give better prediction performance, but larger
# fitting time
rbm.n_components = 100
logistic.C = 6000.0

# Training RBM-Logistic Pipeline
classifier.fit(X_train, Y_train)

# Training Logistic regression
logistic_classifier = linear_model.LogisticRegression(C=100.0)
logistic_classifier.fit(X_train, Y_train)

####################### Testing ############################

print()
print("Logistic regression using RBM features:\n%s\n" % (
    metrics.classification_report(
        Y_test,
        classifier.predict(X_test))))

print("Logistic regression using raw pixel features:\n%s\n" % (
    metrics.classification_report(
        Y_test,
        logistic_classifier.predict(X_test))))

