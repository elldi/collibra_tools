#!/usr/bin/env python

"""
Imports
"""
import sys
import argparse
import requests
import json
import re
import shlex

#-----------
# Functions
#-----------

    

def createDomain():
    print("Domain Created")

def findDomainType():
    print("Domain Type Found!")

def createAsset(assetName, domainId, assetTypeId):
    
    headers = {'Content-Type' : 'application/json'}
    payload = {"name": assetName,"domainId": domainId,"typeId": assetTypeId}
    payload = json.dumps(payload)
    r = requests.post('http://localhost:4400/rest/2.0/assets',headers=headers, data=payload, auth=('Admin','admin'))
    print r.json()        
    return True

def findAssetType(assetTypeName):
    try:
        r = requests.get('http://localhost:4400/rest/2.0/assetTypes?name='+ assetTypeName + '&nameMatchMode=ANYWHERE', auth=('Admin', 'admin'))
        print r.json()['total']
        if(r.json()['total'] > 0):
           return r.json()['results'][0]['id']
        return ''
    except ValueError:
        return False

def init():
    ##fileLoc = raw_input("Enter test script: ")

    fileObject = open('C:\Users\elliot\Documents\personal\workflow_dev_tools\\res\\nAT1.coltest', 'r')

    for line in fileObject:
        line = line.strip()
        if(line == '\n'):
            continue
        
        ## Remove comment lines
        p = re.compile("##")
        matcher = p.match(line)
        if(matcher is not None):
            continue

        ## split by space
        splitBySpace = shlex.split(line)
        
        if(splitBySpace[0] == 'create'):
            print(splitBySpace)
            if(splitBySpace[1] == 'asset'):
                assetTypeId = findAssetType(splitBySpace[7])
                

    fileObject.close()
        


#-----------
# Main
#-----------

init()

