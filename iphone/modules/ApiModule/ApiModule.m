/**
 * Appcelerator Titanium Mobile
 * Copyright (c) 2009 by Appcelerator, Inc. All Rights Reserved.
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 */
#ifdef MODULE_TI_API

#import "ApiModule.h"

@implementation ApiModule

#pragma mark startModule

- (void) logJavascript: (NSString *) severityString description: (id) loggedObject;
{
	if(loggedObject == nil){
		loggedObject = severityString;
		severityString = @"info";
	}

	NSLog(@"JS severity:%@ note:%@",severityString,loggedObject);
}

- (BOOL) startModule;
{
	TitaniumInvocationGenerator * invocGen = [TitaniumInvocationGenerator generatorWithTarget:self];
	
	[(ApiModule *)invocGen logJavascript:nil description:nil];
	NSInvocation * logInvoc = [invocGen invocation];
	
	NSDictionary * moduleDict = [NSDictionary dictionaryWithObjectsAndKeys:
			logInvoc,@"log",
			[TitaniumJSCode codeWithString:@"function(args){Ti.API.log('debug',args);}"],@"debug",
			[TitaniumJSCode codeWithString:@"function(args){Ti.API.log('error',args);}"],@"error",
			[TitaniumJSCode codeWithString:@"function(args){Ti.API.log('warn',args);}"],@"warn",
			[TitaniumJSCode codeWithString:@"function(args){Ti.API.log('info',args);}"],@"info",
			[TitaniumJSCode codeWithString:@"function(args){Ti.API.log('trace',args);}"],@"trace",
			[TitaniumJSCode codeWithString:@"function(args){Ti.API.log('notice',args);}"],@"notice",
			[TitaniumJSCode codeWithString:@"function(args){Ti.API.log('critical',args);}"],@"critical",
			[TitaniumJSCode codeWithString:@"function(args){Ti.API.log('fatal',args);}"],@"fatal",
			nil];
	[[[TitaniumHost sharedHost] titaniumObject] setObject:moduleDict forKey:@"API"];
	
	return YES;
}

@end

#endif