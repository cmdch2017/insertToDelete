# insertToDelete
这个文件功能非常好，能将输入的insert语句的脚本文件转换成delete语句的脚本文件。比如insert into xxx 可以转换为delete from xxx where
比如待处理的文件如下图所示：
![image](https://user-images.githubusercontent.com/32605664/196871532-3f327805-02f9-4af7-9bca-254942f24445.png)
我们的工具，如下图所示
![屏幕截图 2022-10-20 141635](https://user-images.githubusercontent.com/32605664/196870634-b6335482-b9a8-4559-b505-4aed447c8920.png)
点击选择有insert语句的脚本文件
![屏幕截图 2022-10-20 141635](https://user-images.githubusercontent.com/32605664/196870835-05e35d5b-11f4-4c63-8bd5-a95c8f835261.png)
生成目录结构
![image](https://user-images.githubusercontent.com/32605664/196871000-d9e0fee2-2842-42f9-a353-6bad03c77e0f.png)
勾选你认为的主键，生成删除脚本
![image](https://user-images.githubusercontent.com/32605664/196871151-16a57c15-54dc-4a7f-bdef-cf61618ec5db.png)
点击一键生成按钮，会生成result.sql
![image](https://user-images.githubusercontent.com/32605664/196871309-3041adbb-29d8-4e4c-80df-29d4ad4cb547.png)
