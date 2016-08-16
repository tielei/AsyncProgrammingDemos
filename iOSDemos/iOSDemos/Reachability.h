//
//  Reachability.h
//  MyBlogAsyncTaskSeriesDemos
//
//  Created by Charles Zhang on 16/3/24.
//  Copyright © 2016年 tielei. All rights reserved.
//

#import <Foundation/Foundation.h>

#import <SystemConfiguration/SystemConfiguration.h>

extern NSString *const networkStatusNotificationInfoKey;

extern NSString *const kReachabilityChangedNotification;


typedef NS_ENUM(uint32_t, NetworkStatus) {
    NotReachable = 0,
    ReachableViaWiFi = 1,
    ReachableViaWWAN = 2
};

@interface Reachability : NSObject {
@private
    SCNetworkReachabilityRef reachabilityRef;
}

/**
 * 开始网络状态监听
 */
- (BOOL)startNetworkMonitoring;
/**
 * 结束网络状态监听
 */
- (BOOL)stopNetworkMonitoring;
/**
 * 同步获取当前网络状态
 */
- (NetworkStatus) currentNetworkStatus;

@end
