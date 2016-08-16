//
//  Reachability.m
//  MyBlogAsyncTaskSeriesDemos
//
//  Created by Charles Zhang on 16/3/24.
//  Copyright © 2016年 tielei. All rights reserved.
//

#import "Reachability.h"

#import <sys/socket.h>
#import <netinet/in.h>

NSString *const networkStatusNotificationInfoKey = @"networkStatus";

NSString *const kReachabilityChangedNotification = @"NetworkReachabilityChangedNotification";

@implementation Reachability


- (instancetype)init {
    self = [super init];
    if (self) {
        struct sockaddr_in zeroAddress;
//        bzero(&zeroAddress, sizeof(zeroAddress));
        memset(&zeroAddress, 0, sizeof(zeroAddress));
        zeroAddress.sin_len = sizeof(zeroAddress);
        zeroAddress.sin_family = AF_INET;
        
        reachabilityRef = SCNetworkReachabilityCreateWithAddress(kCFAllocatorDefault, (const struct sockaddr*)&zeroAddress);

    }
    
    return self;
}

- (void)dealloc {
    if (reachabilityRef) {
        CFRelease(reachabilityRef);
    }
}

static void ReachabilityCallback(SCNetworkReachabilityRef target, SCNetworkReachabilityFlags flags, void* info) {
    
    Reachability *reachability = (__bridge Reachability *) info;
    
    @autoreleasepool {
        NetworkStatus networkStatus = [reachability currentNetworkStatus];
        [[NSNotificationCenter defaultCenter] postNotificationName:kReachabilityChangedNotification object:reachability userInfo:@{networkStatusNotificationInfoKey : @(networkStatus)}];
    }
}

- (BOOL)startNetworkMonitoring {
    SCNetworkReachabilityContext context = {0, (__bridge void * _Nullable)(self), NULL, NULL, NULL};
    
    if(SCNetworkReachabilitySetCallback(reachabilityRef, ReachabilityCallback, &context)) {
        if(SCNetworkReachabilityScheduleWithRunLoop(reachabilityRef, CFRunLoopGetCurrent(), kCFRunLoopDefaultMode)) {
            return YES;
        }
        
    }
    
    return NO;
}

- (BOOL)stopNetworkMonitoring {
    return SCNetworkReachabilityUnscheduleFromRunLoop(reachabilityRef, CFRunLoopGetCurrent(), kCFRunLoopDefaultMode);
}

- (NetworkStatus) currentNetworkStatus {
    //TODO:
    return ReachableViaWiFi;
}


@end
