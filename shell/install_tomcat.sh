#!/bin/bash
#下载解压 Tomcat

echo -e "\033[32m START... \033[0m"

#目标路径
INSTALL_PATH=/opt/software
#压缩包
PACKAGE_NAME=apache-tomcat-7.0.75.tar.gz
#下载路径
DOWNLOAD_PATH=https://archive.apache.org/dist/tomcat/tomcat-7/v7.0.75/bin/apache-tomcat-7.0.75.tar.gz
#解压后的文件夹名称
DIR_NAME=apache-tomcat-7.0.75

#判断目录是否存在
if [ ! -d $INSTALL_PATH ]; then
  #创建文件夹
  mkdir $INSTALL_PATH
  echo -e "\033[32m $INSTALL_PATH 文件夹创建成功 \033[0m"
else
  echo -e "\033[032m $INSTALL_PATH 文件夹存在 \033[0m"
fi

#切换到安装目录
cd $INSTALL_PATH

#如果不存在则下载
if [ ! -e $INSTALL_PATH/$PACKAGE_NAME ]; then
  #下载
  wget -P $INSTALL_PATH $DOWNLOAD_PATH
  echo -e "\033[32m 下载成功: $PACKAGE_NAME \033[0m"
fi

#判断包是否存在
if [ -e $INSTALL_PATH/$PACKAGE_NAME ]; then
  if [ ! -d $INSTALL_PATH/$DIR_NAME ]; then
    #解压
    tar -zxvf $INSTALL_PATH/$PACKAGE_NAME
    echo -e "\033[32m 解压压缩包成功! \033[0m"
  else
    echo -e "\033[32m 解压后的文件已存在: $DIR_NAME \033[0m"
  fi
else
  echo -e "\e[1;41m error:Tomcat 压缩包不存在, 原因: 下载失败或其他 \e[0m"
  exit 0
fi

echo -e "\033[32m END \033[0m"
