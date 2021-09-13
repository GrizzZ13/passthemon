执行命令：

**jmeter -JthreadCount= -n -t [jmx file] -l [result file] -e -o [Path to output folder]**

**-JthreadCount: 线程数 **
**-n：非GUI模式执行JMeter**
**-t：执行测试文件所在的位置**
**-l：指定生成测试结果的保存文件，jtl文件格式**
**-e：测试结束后，生成测试报告**
**-o：指定测试报告的存放位置**

**ex**
**jmeter -n -t C:\Users\14475\Desktop\PassThemOn\PassThemOnPerformanceTest\-test\PassThemOn.jmx -l C:\Users\14475\Desktop\PassThemOn\PassThemOnPerformanceTest\-test\testdata.csv -e -o C:\Users\14475\Desktop\PassThemOn\PassThemOnPerformanceTest\-test\report**

提交命令:
**git add.**
**git commit -m"comment"**
**git rm --cached ./data/newImages.json**
**git commit --amend -CHEAD**
**git push**
