import os
import re

# 定义要替换的包名
old_package = "com.jiuxi.module.system"
new_package = "com.jiuxi.module.sys"

# 定义要搜索的目录
base_dir = r"D:\jiuxi\project\keycloak-sb-sso\ps-be\src\main\java\com\jiuxi\module\sys"

# 遍历所有Java文件
for root, dirs, files in os.walk(base_dir):
    for file in files:
        if file.endswith(".java"):
            file_path = os.path.join(root, file)
            
            # 读取文件内容
            with open(file_path, 'r', encoding='utf-8') as f:
                content = f.read()
            
            # 替换包声明
            content = re.sub(rf'package {old_package}', f'package {new_package}', content)
            
            # 替换导入语句
            content = re.sub(rf'import {old_package}', f'import {new_package}', content)
            
            # 写回文件
            with open(file_path, 'w', encoding='utf-8') as f:
                f.write(content)
                
print("包名更新完成！")