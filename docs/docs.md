## 说明

### docs下目录说明
* config目录：存放所有的官方默认初始配置文件
* docker目录：存放运行docker容器所需的所有文件，包括docker-compose.yml文件、配置文件、shell脚本文件等，可以进行多级拆分
    * docker/cluster目录：存放所有集群容器相关的文件
    * docker/single目录：存放所有单节点容器相关的文件
* md目录：存放所有".md"文档说明文件[docs.md除外](docs.md)，当文档数量过多时，可以按软件类型进行多级拆分（如：elk/es）
* img目录：存放所有".md"文档说明所需的图片，当图片数量过多时，可以软件类型拆分（如：elk/es）
* shell目录：存放所有非创建dokcer容器的shell脚本文件，创建docker容器的shell脚本存放到docker目录下。可以进行多级拆分
    * shell/learning目录：存放所有学习shell脚本过程中的相关脚本文件
    * shell/wsl2/startup目录：存放win10开机自动启动wsl2中的相关应用的脚本文件
    * test.sh文件：仅用来测试shell脚本用，其中内容可以任意删除和修改



