#!/usr/bin/env python

"""
Imports
"""
import sys
import argparse
import requests
import json

#-----------
# Functions
#-----------

def isConnected():
    try:
        r = requests.get('http://localhost:4400/rest/2.0/application/info')
        ##print r.json()['buildNumber']
        return True
    except ValueError:
        return False

def getAssetTypes():
    try:
        r = requests.get('http://localhost:4400/rest/2.0/assetTypes', auth=('Admin', 'admin'))
        print r.json()['total']
        return True
    except ValueError:
        return False

def createUser():

    headers = {'Content-Type' : 'application/json'}
    payload = {"userName": "elliot","firstName": "Elliot","lastName": "Dines","emailAddress": "email@email.com","gender": "MALE"}
    payload = json.dumps(payload)
    r = requests.post('http://localhost:4400/rest/2.0/users',headers=headers, data=payload, auth=('Admin','admin'))
    print r.json()        
    return True

def getAllWorkflows():
    try:
        r = requests.get('http://localhost:4400/rest/2.0/workflowDefinitions?enabled=true&limit=0&offset=0', auth=('Admin', 'admin'))
        return r.json()['results']
    except ValueError:
        return False  

def getWorkflowId():
    try:
        r = requests.get('http://localhost:4400/rest/2.0/workflowDefinitions?enabled=true&limit=0&name=Approval%20Process&offset=0', auth=('Admin', 'admin'))
        return r.json()['results'][0]['id']
    except ValueError:
        return False

def startWorkflow(workflowId, assetId):

    headers = {'Content-Type' : 'application/x-www-form-urlencoded'}
    payload = {'items': assetId, 'itemResourceType': 'TE', 'start': '1526315246216'}
    url = 'http://localhost:4400/rest/latest/workflow/' + workflowId + '/start'
    print url
    r = requests.post(url, headers=headers, data=payload, auth=('Admin','admin'))
    return r.text
    

def init():
    print "Welcome to the DT2 Workflow testing suite!"

    print "Collibra Connection? " + isConnected() 

    workflowId = raw_input("Enter workflow definition id: ")
    assetId = raw_input("Enter asset id: ")

    print startWorkflow(workflowId, assetId)

def testing():
    workflows = getAllWorkflows()

    for workflowDef in workflows:
        print(workflowDef['name'])


        
#-----------
# Main
#-----------


#init()
testing()

