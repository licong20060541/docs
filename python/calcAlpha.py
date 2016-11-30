#!/usr/bin/env python3
# -*- coding: utf-8 -*-
num = input('please input float alpha like 0.4: ')
result = 256 * float(num) 
result2 = int(result)
print('256 * ' + num + '=' + str(result2))
print(str(result2) + ' to hex is ' + hex(result2) ) 
