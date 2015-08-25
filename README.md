# updiff 是什么？

updiff 是一个增量更新(升级)的工具，支持备份、更新、异常恢复功能。依据 Git 两个提交版本号提取差异文件进行更新操作。依赖maven、git并可以和jenkins无缝隙集成。

# updiff 能做什么？

1、大型项目模块化后，会分解为很多子模块，当升级时需要靠人工挑选那些包需要升级（每次升级都全量部署安装的不适用updiff），然而人工出包会出现少打包、打错包等问题。updiff可以依赖两次提交的Git版本提取差异文件进行升级操作。

2、当升级后发现有错误，想退回到之前版本，updiff可以一键恢复。

# 如何使用？

### 出增量包时，增加maven插件并指定 Git 的提交版本

```
<plugin>
	<groupId>com.sand.updiff</groupId>
	<artifactId>updiff-mvn-plugin</artifactId>
	<version>1.0.4</version>
	<configuration>
		<!-- HEAD 为最新版本-->
		<newGitVersion>HEAD</newGitVersion>
		<!-- 83d583f 为最后一次上线版本-->
		<oldGitVersion>83d583f</oldGitVersion>
	</configuration>
	<executions>
		<execution>
			<id>updiff</id>
			<phase>prepare-package</phase>
			<goals>
				<goal>updiff</goal>
			</goals>
		</execution>
	</executions>
</plugin>

```

### 编译打包updiff upper升级工具

#### 在updiff根目录下执行以下命令

```
clean package -DskipTests=true
```

### 安装updiff-upper-1.0.4-assembly.tar.gz

#### 解压
```
cd $HOME

tar -zxvf updiff-upper-1.0.4-assembly.tar.gz

```
#### 增加 UPPER_HOME 环境变量
```
# 编辑profile
vi .bash_profile

# 加入以下两行并保存退出
export UPPER_HOME=$HOME/updiff-upper-1.0.4
export PATH=$UPPER_HOME/bin:$PATH

# 使profile生效
source .bash_profile
```
#### 键入 upper 回车 出现以下信息证明安装成功
```
----------------------------------------------------------------------------------------------------
用法：
     upper up oldDir newPath                    执行更新，包括：备份、更新、恢复
或：
     upper backup oldDir newPath [backupPath]   执行备份，仅备份不做更新
                                                backupDir 可选，默认值为：oldDir_backup_yyyyMMddHHmmss
或：
     upper recovery backupDir                   执行恢复，根据指定备份文件恢复
其中：
     oldDir   要更新的文件夹
     newPath  更新包或文件夹，它的文件结构必须和oldDir的一致。更新包格式只能为.zip或.tar.gz，如果包内只有一个文
              件夹且名字和oldDir相等，则认为是更新包的根路径
----------------------------------------------------------------------------------------------------
```

