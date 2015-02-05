#!/usr/bin/env python
# coding: utf-8

import sys
import cyclone.web
import datetime
import Image
import json
import MySQLdb
import MySQLdb.cursors
from collections import OrderedDict as order
from twisted.python import log
from twisted.internet import reactor, defer
from twisted.enterprise import adbapi

db = adbapi.ConnectionPool("MySQLdb", db="pimpmypizza", user="root", passwd="pimpmaneiro", cursorclass=MySQLdb.cursors.DictCursor)

default = lambda x: x if x else "%nbsp;"

#----------------------------------------------------------------------------------------------#
#                                          Home Page                                           
#----------------------------------------------------------------------------------------------#
class MainHandler(cyclone.web.RequestHandler):
    
    def get(self):
        #        print self.request.headers["User-Agent"].lower()
        #        res = re.matches('Android|BlackBerry|Blazer|BOLT|Nokia|Samsung|Doris|Mobile|iPhone|Dorothy|Fennec|MIB|Maemo|Iris|Minimo|NetFront|Mini|SonyEricsson|Skyfire|Teleca', self.request.headers["User-Agent"].lower())
        #        print res
        #        if(res):
        #            print "nigger"
        #        else:
        args = {}
        self.render("index.html", **args)

        
#----------------------------------------------------------------------------------------------#
#
#                                 main - execute the classes
#
#----------------------------------------------------------------------------------------------#
def main():
    log.startLogging(sys.stdout)
    
    settings = {
        "static_path": "./client/",
        "template_path": "./client/",
        "debug": True,
        "cookie_secret": "pimpdivino"
    }
       
    application = cyclone.web.Application([
        (r"^/$", MainHandler)
    ], **settings)
    
    reactor.listenTCP(80, application)
    reactor.run()

if __name__ == "__main__":
    main()