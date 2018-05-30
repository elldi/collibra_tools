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


class DataManip:
    
    def __init__(self, baseURL):
        self.baseURL = baseURL
        
    def createDomain(self):
        print("Domain Created")

    def findDomainType(self):
        print("Domain Type Found!")

    def createAsset(self,assetName, domainId, assetTypeId):
        
        headers = {'Content-Type' : 'application/json'}
        payload = {"name": assetName,"domainId": domainId,"typeId": assetTypeId}
        payload = json.dumps(payload)
        r = requests.post(self.baseURL + '/rest/2.0/assets',headers=headers, data=payload, auth=('Admin','admin'))
        print r.json()        
        return True

    def findAssetType(self,assetTypeName):
        try:
            r = requests.get(self.baseURL + '/rest/2.0/assetTypes?name='+ assetTypeName + '&nameMatchMode=ANYWHERE', auth=('Admin', 'admin'))
            print r.json()['total']
            if(r.json()['total'] > 0):
               return r.json()['results'][0]['id']
            return ''
        except ValueError:
            return False

#-----------
# Functions
#-----------

def init():
    ##fileLoc = raw_input("Enter test script: ")

    fileObject = open('C:\Users\elliot\Documents\personal\workflow_dev_tools\\res\\nAT1.coltest', 'r')

    dataManip = DataManip("http://localhost:4400")
    
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
                assetTypeId = dataManip.findAssetType(splitBySpace[7])
                

    fileObject.close()
        


#-----------
# Main
#-----------

init()

