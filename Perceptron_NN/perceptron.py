#!/usr/bin/env python
# -*- coding: utf-8 -*-

import os,numpy
from os.path import expanduser

list = ['-']    #'-'=x0
tmp = [1]
olist = []
# olist = [tmp]
fArr = []
dArr = []
# dArr = [fArr]   #2d array need to initialize 1d array first
nnlist = ["-", ",", ".","/","@"]

# def dot_product(values, weights):
#     return sum(value * weight for value, weight in zip(values, weights))

for file in os.listdir(expanduser("~/") + '/Desktop/Gogate_AML/Assignments/Assignment2/homework/train/ham'):
    if file.endswith(".txt"):
        with open(expanduser("~/") + '/Desktop/Gogate_AML/Assignments/Assignment2/homework/train/ham／'+file) as reader:
            fArr = []
            for line in reader:
                for word in line.split():
                    # print word,
                    if word in nnlist:
                        continue
                    else:
                        fArr.append(word)   #read into file
                        if word in list:    #build term list
                            continue
                        else:
                            list.append(word)
            # print dArr
            # print '\r'
        dArr.append(fArr)

print len(dArr)

for file in os.listdir(expanduser("~/") + '/Desktop/Gogate_AML/Assignments/Assignment2/homework/train/spam'):
    if file.endswith(".txt"):
        with open(expanduser("~/") + '/Desktop/Gogate_AML/Assignments/Assignment2/homework/train/spam/'+file) as reader:
            fArr = []
            for line in reader:
                for word in line.split():
                    # print word,
                    if word in nnlist:
                        continue
                    else:
                        fArr.append(word)   #read into file
                        if word in list:    #build term list
                            continue
                        else:
                            list.append(word)
            # print dArr
        dArr.append(fArr)

print len(list),len(dArr)

tmp = {}
dict = {}
dict = dict.fromkeys(list,0)


for file in dArr:
    dict['-'] =1
    for word in file:
        if word in dict:
            dict[word] +=1
    # for term in list:
#         ocr = file.count(term)
#         tmp.append(ocr)
        # print ocr,
    # print '\r',len(tmp),'\r'
    olist.append(dict.values())
    
print len(olist),len(dict)  #len(dict) = 10446

wlist = [0]*len(dict)
j=0
dot = 0
eta = 0.1

for i in range(20):
    for ofile in olist: 
        j+=1
        dot = numpy.dot(ofile,wlist)
        if dot>0:
            o=1
        else:
            o=0
        for k in range(len(dict)):
            # print k
            if j <=340:                     #ham==0
                wlist[k] += eta*(0-o)*ofile[k]
            else:                           #spam==1
                wlist[k] += eta*(1-o)*ofile[k]
        # if i > 15:
            # print wlist[0],wlist[4],wlist[8],'\r'
            if j == 1:
                print wlist[k]
    # print j
print "finished!"

tArr = []
#for test data set
p = 0
for file in os.listdir(expanduser("~/") + '/Desktop/Gogate_AML/Assignments/Assignment2/homework/test/ham'):
    if file.endswith(".txt"):
        with open(expanduser("~/") + '/Desktop/Gogate_AML/Assignments/Assignment2/homework/test/ham/'+file) as reader:
            fArr = []
            for line in reader:
                for word in line.split():
                    # print word,
                    fArr.append(word)   #read into file
            dict['-'] =1
            for word in file:
                if word in dict:
                    dict[word] +=1
            dot = numpy.dot(dict.values(),wlist)
            if dot < 0:
                p+=1
            
        tArr.append(dict)
print p,len(tArr)

for file in os.listdir(expanduser("~/") + '/Desktop/Gogate_AML/Assignments/Assignment2/homework/test/spam'):
    if file.endswith(".txt"):
        with open(expanduser("~/") + '/Desktop/Gogate_AML/Assignments/Assignment2/homework/test/spam/'+file) as reader:
            fArr = []
            for line in reader:
                for word in line.split():
                    # print word,
                    fArr.append(word)   #read into file
            dict['-'] =1
            for word in file:
                if word in dict:
                    dict[word] +=1
            dot = numpy.dot(dict.values(),wlist)
            if dot >= 0:
                p+=1
            
        tArr.append(dict)
print p,len(tArr)
print "done!"
    
        
     
