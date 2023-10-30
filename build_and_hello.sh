#!/bin/bash

# Androidプロジェクトのビルドコマンドを追加
./gradlew build

# ビルドが成功した場合に "Hello" を出力
if [ $? -eq 0 ]; then
  echo "Hello"
else
  echo "********* Build failed *********"
fi