# Python爬虫接口

## 命令行调用

**使用方法**
> python crawler.py -bv <视频BV号>

**命令行参数选项**

* -bv <视频BV号>：（必填项）添加后将对此BV号的视频进行爬取
* -o <文件保存路径>：（非必填项）添加后，视频信息将不再打印到标准输出，而是保存到指定路径的文件，如果路径上已经存在文件，则原本文件的内容将被覆盖。
* -config <配置文件路径>：（非必填项）添加后，将读取指定路径上的文件作为配置文件，如果不添加，将默认读取crawler.py同目录下的config.json作为配置文件。
* -p：（非必填项）添加后，会将原本的data对象作为根对象输出，而data对象的内容不变，如果在爬取过程中出现错误，将返回空对象。
* -nl：（非必填项）添加后，将不会把log保存在本地。如果不添加，将默认保存log

**配置文件**
以json格式组织，根对象：

| 字段    | 类型 | 内容     | 备注                          |
| ------- | ---- | -------- | ----------------------------- |
| cookie    | str  | 用户cookie   | 具有时效性的bilibili网站cookie，如果cookie过期或错误，将无法爬出IP地址，但是其他字段能正常爬出  |


**效果**

将从标准输出打印结果字符串（如果调用时指定-o参数，则不会打印到标准输出，而是输出到文件），字符串以json格式组织，内容如下。

根对象：

| 字段    | 类型 | 内容     | 备注                          |
| ------- | ---- | -------- | ----------------------------- |
| code    | num  | 返回值   |  0：成功<br /> 1：失败 |
| msg | str  | 错误信息 | 默认为空字符串                       |
| data | obj  | 爬取结果 | 如果code为1，则data字段将为空对象                       |

data对象：

| 字段            | 类型 | 内容         | 备注                   |
| --------------- | ---- | ------------ | ---------------------- |
| video              | obj  | 视频信息     |        |
| comments       | list  | 评论列表    |                        |

video对象：
| 字段    | 类型 | 内容     | 备注                          |
| ------- | ---- | -------- | ----------------------------- |
| video_bid       | str  | 视频bv号    | 形式：“BVXXXXXX”       |
| video_aid       | str  | 视频aid号    | 字符串，但是是数字                       |
| owner_uid       | str  | UP主用户uid |  字符串，但是是数字                      |
| owner_name      | str  | UP主用户名   |                        |
| video_title     | str  | 视频标题     |                        |
| video_partition | str  | 视频分区     |                        |
| video_tables    | str  | 视频分区     |                        |
| video_pubdate   | str  | 视频发布时间 | 格式为"2023-10-08 12:40:32"       |
| video_duration  | num  | 视频时长     | 单位为秒                |
| video_like      | num  | 点赞数       |                        |
| video_coin      | num  | 投币数       |                        |
| video_favorite  | num  | 收藏数       |                        |
| video_share     | num  | 分享数       |                        |
| video_reply     | num  | 评论数       |                        |
| video_dislike   | num  | 点踩数       |                        |
| video_cid       | str  | 视频cid号    | 字符串，但是是数字       |
| video_disc      | str  | 视频简介     |        |

comments对象为一列表，其中所有列表项均为以下对象：

| 字段              | 类型 | 内容     | 备注                     |
| ----------------- | ---- | -------- | ------------------------ |
| user_uid          |      |   str       |  字符串，但是是数字                        |
| user_name         |      |   str       |                          |
| user_ip           |      |   str       |                          |
| user_sex          |      |  str        | 有“保密”、“男”、“女”   |
| comment_date      |      |  str        | 示例："2023-10-08 12:40:32" |
| comment_text      |      |  str        |                          |
| comment_like      |      |  num        | 点赞数量                         |
| comment_reply     |      |  num        | 回复数量                         |

如果调用时指定-o参数，则不会打印到标准输出，而是输出到文件。


## 网络HTTP调用

> /api/crawl

*请求方式：GET
**正文参数：**

| 参数名  | 类型 | 内容                     | 必要性         | 备注                                 |
| ------- | ---- | ------------------------ | -------------- | ------------------------------------ |
| bv      | str  | 视频bv号                | 必要           |   形式：“BVXXXXXX”             |


**效果：**
爬取视频信息并返回

**json回复：**
根对象：

| 字段    | 类型 | 内容     | 备注                          |
| ------- | ---- | -------- | ----------------------------- |
| code    | num  | 返回值   |  0：成功<br /> 1：失败 |
| msg | str  | 错误信息 | 默认为空字符串                       |
| data | obj  | 爬取结果 | 如果code为1，则data字段将为空对象                       |

data对象：

| 字段            | 类型 | 内容         | 备注                   |
| --------------- | ---- | ------------ | ---------------------- |
| video              | obj  | 视频信息     |        |
| comments       | list  | 评论列表    |                        |

video对象：
| 字段    | 类型 | 内容     | 备注                          |
| ------- | ---- | -------- | ----------------------------- |
| video_bid       | str  | 视频bv号    | 形式：“BVXXXXXX”       |
| video_aid       | str  | 视频aid号    | 字符串，但是是数字                       |
| owner_uid       | str  | UP主用户uid |  字符串，但是是数字                      |
| owner_name      | str  | UP主用户名   |                        |
| video_title     | str  | 视频标题     |                        |
| video_partition | str  | 视频分区     |                        |
| video_tables    | str  | 视频分区     |                        |
| video_pubdate   | str  | 视频发布时间 | 格式为"2023-10-08 12:40:32"       |
| video_duration  | num  | 视频时长     | 单位为秒                |
| video_like      | num  | 点赞数       |                        |
| video_coin      | num  | 投币数       |                        |
| video_favorite  | num  | 收藏数       |                        |
| video_share     | num  | 分享数       |                        |
| video_reply     | num  | 评论数       |                        |
| video_dislike   | num  | 点踩数       |                        |
| video_cid       | str  | 视频cid号    | 字符串，但是是数字       |
| video_disc      | str  | 视频简介     |                         |

comments对象为一列表，其中所有列表项均为以下对象：

| 字段              | 类型 | 内容     | 备注                     |
| ----------------- | ---- | -------- | ------------------------ |
| user_uid          |      |   str       |  字符串，但是是数字                        |
| user_name         |      |   str       |                          |
| user_ip           |      |   str       |                          |
| user_sex          |      |  str        | 有“保密”、“男”、“女”   |
| comment_date      |      |  str        | 示例："2023-10-08 12:40:32" |
| comment_text      |      |  str        |                          |
| comment_like      |      |  num        | 点赞数量                         |
| comment_reply     |      |  num        | 回复数量                         |