//
//  AipOCR.h
//  AipOCR
//
//  Created by xuyazhong on 18/1/8.
//  Copyright © 2018年 xyz. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <React/RCTBridgeModule.h>

typedef void(^dissmissPages)(void);
@interface AipOCR : NSObject<RCTBridgeModule>

@property(nonatomic, copy) dissmissPages block;

@end

@interface OCRModel : NSObject

@property (nonatomic, copy) NSString *address;
@property (nonatomic, copy) NSString *idNumber;
@property (nonatomic, copy) NSString *birthday;
@property (nonatomic, copy) NSString *name;
@property (nonatomic, copy) NSString *gender;
@property (nonatomic, copy) NSString *ethnic;

@end
