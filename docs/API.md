## 管理视频
### 添加视频信息进入数据库

> /api/v1/videos

*请求方式：POST*
**正文参数：**

| 参数名  | 类型 | 内容                     | 必要性         | 备注                                 |
| ------- | ---- | ------------------------ | -------------- | ------------------------------------ |
| bv      | str  | 视频bv号                | 必要           |   形式：“BVXXXXXX”                 |

**效果：**
服务器将爬取指定视频的基本信息和评论信息，并对每一条评论进行情绪感知，然后保存进数据库。

如果服务器已经保存了此BV号的视频，或是添加时出现错误，则数据库不会更新。

**json回复：**
根对象：

| 字段    | 类型 | 内容     | 备注                          |
| ------- | ---- | -------- | ----------------------------- |
| code    | num  | 返回值   | 200：成功<br />405：数据库中已经存在此视频<br />406：添加时出现错误 |
| msg | str  | 错误信息 | 默认为空字符串                       |


## 从数据库中删除视频

> /api/v1/videos

请求方式：DELETE
**正文参数：**

| 参数名  | 类型 | 内容                     | 必要性         | 备注                                 |
| ------- | ---- | ------------------------ | -------------- | ------------------------------------ |
| bv      | str  | 视频bv号                | 必要           |    形式：“BVXXXXXX”               |

**效果：**
将删除指定视频在数据库中的基本信息与所有评论，包括情绪感知结果。

**json回复：**
根对象：

| 字段    | 类型 | 内容     | 备注                          |
| ------- | ---- | -------- | ----------------------------- |
| code    | num  | 返回值   | 200：成功<br />407：数据库找不到此资源 |
| msg | str  | 错误信息 | 默认为空字符串                       |

### 更新视频信息

> /api/v1/videos

*请求方式：PUT
**正文参数：**

| 参数名  | 类型 | 内容                     | 必要性         | 备注                                 |
| ------- | ---- | ------------------------ | -------------- | ------------------------------------ |
| bv      | str  | 视频bv号                | 必要           |   形式：“BVXXXXXX”                  |

**效果：**
重新爬取指定视频基本信息与评论信息，并进行情绪感知，并替换原本视频的所有信息。

注意，如果服务器原本没有此BV号的视频信息，则服务器将不会爬取信息。

**json回复：**
根对象：

| 字段    | 类型 | 内容     | 备注                          |
| ------- | ---- | -------- | ----------------------------- |
| code    | num  | 返回值   | 200：成功<br />407：数据库找不到此资源 |
| msg | str  | 错误信息 | 默认为空字符串                       |

## 获取指定视频信息（Deprecated）

> /api/v1/videos

*请求方式：GET
**正文参数：**

| 参数名  | 类型 | 内容                     | 必要性         | 备注                                 |
| ------- | ---- | ------------------------ | -------------- | ------------------------------------ |
| bv      | str  | 视频bv号                | 必要           |   形式：“BVXXXXXX”             |
| autopost      | num  | 1或0                | 必要           | 值为1时，如果数据库没有此BV视频的信息，服务器将自动爬取并分析此BV视频的评论信息，随后将结果返回                                     |

**效果：**
获取视频信息，将不会更新或爬取视频信息。

如果autopost为1，而且数据库里原本没有此BV号的视频信息，则服务器将自动爬取视频信息，然后将处理后的视频信息返回；如果autopost为1，但是数据库里已经有此BV号的视频信息，则服务器不会重新爬取此BV号的视频信息，而是将视频信息直接返回。

**json回复：**
根对象：

| 字段    | 类型 | 内容     | 备注                          |
| ------- | ---- | -------- | ----------------------------- |
| code    | num  | 返回值   | 200：成功<br />407：数据库找不到此资源<br />408：数据库找不到此资源，且添加失败（当autopost为1时） |
| msg | str  | 错误信息 | 默认为空字符串                       |
| data     | obj  | 数据        |                               |

data对象：

| 字段    | 类型 | 内容     | 备注                          |
| ------- | ---- | -------- | ----------------------------- |
| video       | object  | 视频信息    |        |
| comments       | list  | 评论列表    |                        |

video对象：
| 字段    | 类型 | 内容     | 备注                          |
| ------- | ---- | -------- | ----------------------------- |
| operation_time    | str  | 时间   | 后端处理这条视频的评论的时间，示例："2023-10-08 12:40:32" |
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
| video_desc      | str  | 视频简介     |                        |


comments列表，其中所有对象都以以下格式组织：

| 字段  | 类型 | 内容     | 备注                          |
| ------- | ---- | -------- | ----------------------------- |
| user_uid          |      |   str       |  字符串，但是是数字                        |
| user_name         |      |   str       |                          |
| user_ip           |      |   str       |                          |
| user_sex          |      |  str        | 有“保密”、“男”、“女”   |
| comment_date      |      |  str        | 示例："2023-10-08 12:40:32" |
| comment_text      |      |  str        |                          |
| comment_like      |      |  num        | 点赞数量                         |
| comment_reply     |      |  num        | 回复数量                         |
| emotion     | str  | 情感        |                               |


## 感知由用户直接上传的评论信息

> /api/v1/comments

*请求方式：POST
**正文参数：**

| 参数名  | 类型 | 内容                     | 必要性         | 备注                                 |
| ------- | ---- | ------------------------ | -------------- | ------------------------------------ |

**附带信息：**

根对象：
| 字段    | 类型 | 内容     | 备注                          |
| ------- | ---- | -------- | ----------------------------- |
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
| video_desc      | str  | 视频简介     |                        |


comments列表，其中所有对象都以以下格式组织：

| 字段              | 类型 | 内容     | 备注                     |
| ----------------- | ---- | -------- | ------------------------ |
| user_uid          |         str|       |  字符串，但是是数字                        |
| user_name         |         str|       |                          |
| user_ip           |         str |      |                          |
| user_sex          |        str   |     | 有“保密”、“男”、“女”   |
| comment_date      |        str    |    | 示例："2023-10-08 12:40:32" |
| comment_text      |        str     |   |                          |
| comment_like      |        num      |  | 点赞数量                         |
| comment_reply     |        num       | | 回复数量                         |


**效果：**
获取视频信息，需要预先准备好评论文件（json格式）。

将保存这些信息进入数据库。

如果数据库内已经存在该视频，则不会加入。

**json回复：**
根对象：

| 字段    | 类型 | 内容     | 备注                          |
| ------- | ---- | -------- | ----------------------------- |
| code    | num  | 返回值   | 200：成功<br />409：失败<br />405：数据库中已经存在此视频 |
| msg | str  | 错误信息 | 默认为空字符串                       |
| data     | obj  | 数据        |                               |

data对象：

| 字段    | 类型 | 内容     | 备注                          |
| ------- | ---- | -------- | ----------------------------- |
| video              | obj  | 视频信息     |        |


video对象：
| 字段    | 类型 | 内容     | 备注                          |
| ------- | ---- | -------- | ----------------------------- |
| operation_time    | str  | 时间   | 后端处理这条视频的评论的时间，示例："2023-10-08 12:40:32" |
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
| video_desc      | str  | 视频简介     |                        |


## 获取视频列表

> /api/v1/videos/list

*请求方式：GET*
**正文参数：**

| 参数名  | 类型 | 内容                     | 必要性         | 备注                                 |
| ------- | ---- | ------------------------ | -------------- | ------------------------------------ |


**效果：**
获取视频基本信息列表。

**json回复：**
根对象：

| 字段    | 类型 | 内容     | 备注                          |
| ------- | ---- | -------- | ----------------------------- |
| code    | num  | 返回值   | 200：成功<br />409：请求失败 |
| msg | str  | 错误信息 | 默认为空字符串                       |
| data     | obj  | 数据        |                               |

data对象：

| 字段    | 类型 | 内容     | 备注                          |
| ------- | ---- | -------- | ----------------------------- |
| videos    | list  | 视频基本信息   |  |

videos列表里的对象：

| 字段    | 类型 | 内容     | 备注                          |
| ------- | ---- | -------- | ----------------------------- |
| video_bid       | str  | BV号 |    形式：“BVXXXXXX”                    |
| video_title     | str  | 视频标题     |                        |
| operation_time     | str  | 处理时间    |  后端处理这条视频的评论的时间，示例："2023-10-08 12:40:32"                      |


## 快速感知评论信息

> /api/v1/sentence

*请求方式：POST
**正文参数：**

| 参数名  | 类型 | 内容                     | 必要性         | 备注                                 |
| ------- | ---- | ------------------------ | -------------- | ------------------------------------ |

**附带信息：**
| 字段    | 类型 | 内容     | 备注                          |
| ------- | ---- | -------- | ----------------------------- |
| comments    | list  | 评论列表   | |

comments列表，其中所有对象都是字符串，每个字符串是一条评论。


**效果：**
感知单独的评论内容，数据也会进入数据库。


**json回复：**
根对象：

| 字段    | 类型 | 内容     | 备注                          |
| ------- | ---- | -------- | ----------------------------- |
| code    | num  | 返回值   | 200：成功<br />409：失败 |
| msg | str  | 错误信息 | 默认为空字符串                       |
| data     | obj  | 数据        |                               |

data对象：

| 字段    | 类型 | 内容     | 备注                          |
| ------- | ---- | -------- | ----------------------------- |
| operation_time    | str  | 时间   | 后端处理这些评论的时间，示例："2023-10-08 12:40:32" |
| comments | list  | 评论列表 |                        |

comments列表，其中所有对象都以以下格式组织：

| 字段  | 类型 | 内容     | 备注                          |
| ------- | ---- | -------- | ----------------------------- |
| content     | str  | 评论内容        |                               |
| emotion     | str  | 情感        |                               |


## 获取指定视频信息（附带评论筛选）（包含分页）

> /api/v1/videos/page/filter

*请求方式：GET
**正文参数：**

| 参数名        | 类型 | 内容                     | 必要性         | 备注                                 |
| -------       | ---- | ------------------------ | -------------- | ------------------------------------ |
| bv            | str  | 视频bv号                | 必要           |   形式：“BVXXXXXX”             |
| autopost      | num  | 1或0                | 必要           | 值为1时，如果数据库没有此BV视频的信息，服务器将自动爬取并分析此BV视频的评论信息，随后将结果返回                                     |
| n_per_page      | num  | 非负整数                | 必要           | 值不为0时，表明需分页时每页的评论数量，值为0时，表明不需要分页                                     |
| pn      | num  | 非负整数                | 必要           | 如果n_per_page不为0，则返回pn对应页的评论；如果n_per_page为0，则无论pn为多少，返回所有评论。pn页码从0开始                                     |
| user_ip       | str  |  筛选IP地址         | 非必要 | 如果传多个，应使用逗号分割，如"湖南,湖北"           |
| user_sex      |  str |  筛选性别           | 非必要 | 有“保密”、“男”、“女”，如果传多个，应使用逗号分割   |
| comment_date  |  str |  筛选时间范围        | 非必要 |将两个时间点用逗号隔开，此为闭区间，示例："2023-10-08 12:40:32,2023-10-08 12:41:32"，如果不用逗号分割(例如"2023-10-08 12:40:32")，就是默认下界；如果以逗号起头(例如",2023-10-08 12:41:32")，就是默认上界 |
| comment_like  | str  |  筛选点赞数量范围    | 非必要 | 将上界与下界用逗号分割，此为闭区间，示例："30,40"，如果不用逗号分割(例如"30")，就是默认下界；如果以逗号起头(例如",40")，就是默认上界   |
| comment_reply | str  |  筛选回复数量范围    | 非必要 | 将上界与下界用逗号分割，此为闭区间，示例："30,40"，如果不用逗号分割(例如"30")，就是默认下界；如果以逗号起头(例如",40")，就是默认上界   |
| emotion       | str  |  筛选情感           | 非必要 | 如果传多个，应使用逗号分割，如"伤心,开心"          |

**效果：**
获取指定页的且经过过滤的视频信息，调用此API时，将不会更新或爬取视频信息。

过滤规则：
可以对user_ip、user_sex、comment_date、comment_like、comment_reply、emotion这几个字段添加过滤（过滤变量添加在请求参数中，规则见上面表格），不符合的comment将不会被纳入分页，不会被返回。

分页规则：
1. n_per_page必须为非负整数，且其值不为0时，表明需分页时每页的评论数量，值为0时，表明不需要分页。
2. pn页码从0开始，pn必须为非负整数。
3. 如果n_per_page不为0，则返回pn对应页的评论；如果n_per_page为0，则无论pn为多少，返回所有评论。

如果autopost为1，而且数据库里原本没有此BV号的视频信息，则服务器将自动爬取视频信息，然后将处理后的视频信息返回；如果autopost为1，但是数据库里已经有此BV号的视频信息，则服务器不会重新爬取此BV号的视频信息，而是将视频信息直接返回。

**json回复：**
根对象：

| 字段    | 类型 | 内容     | 备注                          |
| ------- | ---- | -------- | ----------------------------- |
| code    | num  | 返回值   | 200：成功<br />407：数据库找不到此资源<br />408：数据库找不到此资源，且添加失败（当autopost为1时） |
| msg | str  | 错误信息 | 默认为空字符串                       |
| data     | obj  | 数据        |                               |

data对象：

| 字段    | 类型 | 内容     | 备注                          |
| ------- | ---- | -------- | ----------------------------- |
| video       | object  | 视频信息    |        |
| comments       | list  | 评论列表    |                        |
| total_page_num       | num  | 分页总数    |                        |

video对象：
| 字段    | 类型 | 内容     | 备注                          |
| ------- | ---- | -------- | ----------------------------- |
| operation_time    | str  | 时间   | 后端处理这条视频的评论的时间，示例："2023-10-08 12:40:32" |
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
| video_reply     | num  | 评论数       | 是视频的总评论数，包含所有评论（但是评论的子评论不会包含进comments列表）      |
| video_dislike   | num  | 点踩数       |                        |
| video_cid       | str  | 视频cid号    | 字符串，但是是数字       |
| video_desc      | str  | 视频简介     |                        |


comments列表，其中所有对象都以以下格式组织：

| 字段  | 类型 | 内容     | 备注                          |
| ------- | ---- | -------- | ----------------------------- |
| user_uid          |      |   str       |  字符串，但是是数字                        |
| user_name         |      |   str       |                          |
| user_ip           |      |   str       |                          |
| user_sex          |      |  str        | 有“保密”、“男”、“女”   |
| comment_date      |      |  str        | 示例："2023-10-08 12:40:32" |
| comment_text      |      |  str        |                          |
| comment_like      |      |  num        | 点赞数量                         |
| comment_reply     |      |  num        | 回复数量                         |
| emotion     | str  | 情感        |                               |


## 获取情感分析总体统计信息

> /api/v1/videos/statistics/overview

*请求方式：GET
**正文参数：**

| 参数名  | 类型 | 内容                     | 必要性         | 备注                                 |
| ------- | ---- | ------------------------ | -------------- | ------------------------------------ |
| bv      | str  | 视频bv号                | 必要           |   形式：“BVXXXXXX”             |



**效果：**
获取视频评论每种情绪的人数占比信息，调用此API时，将不会更新或爬取视频信息。

包含以下统计信息：
1. 每一个情感分类的人数


**json回复：**
根对象：

| 字段    | 类型 | 内容     | 备注                          |
| ------- | ---- | -------- | ----------------------------- |
| code    | num  | 返回值   | 200：成功<br />407：数据库找不到此资源<br />408：数据库找不到此资源，且添加失败（当autopost为1时） |
| msg | str  | 错误信息 | 默认为空字符串                       |
| data     | obj  | 数据        |                               |

data对象：

| 字段    | 类型 | 内容     | 备注                          |
| ------- | ---- | -------- | ----------------------------- |
| emotion       | list  | 所有出现的情感分类，列表中的元素均为字符串    |        |
| num_person       | list  | 每个情感分类的人数，列表中的元素均为num    | 与emotion列表的长度相同，同下标元素也对应                       |



## 获取情感分析IP统计信息

> /api/v1/videos/statistics/ip

*请求方式：GET
**正文参数：**

| 参数名  | 类型 | 内容                     | 必要性         | 备注                                 |
| ------- | ---- | ------------------------ | -------------- | ------------------------------------ |
| bv      | str  | 视频bv号                | 必要           |   形式：“BVXXXXXX”             |



**效果：**
获取视频评论与IP相关的统计信息，调用此API时，将不会更新或爬取视频信息。

包含以下统计信息：
1. 同一IP对视频的情感分类占比。
2. 对于所有情感分类的评论IP占比。

**json回复：**
根对象：

| 字段    | 类型 | 内容     | 备注                          |
| ------- | ---- | -------- | ----------------------------- |
| code    | num  | 返回值   | 200：成功<br />407：数据库找不到此资源<br />408：数据库找不到此资源，且添加失败（当autopost为1时） |
| msg | str  | 错误信息 | 默认为空字符串                       |
| data     | obj  | 数据        |                               |

data对象：

| 字段    | 类型 | 内容     | 备注                          |
| ------- | ---- | -------- | ----------------------------- |
| emotion       | list  | 所有出现的情感分类，列表中的元素均为字符串    |        |
| ip_ratio_per_emotion       | list  | 每个情感分类里，不同ip的人数占比，列表中的元素均为object    | 与emotion列表的长度相同，同下标元素也对应                       |
| ip       | list  | 所有出现的ip，列表中的元素均为字符串    |                        |
| emotion_ratio_per_ip       | list  | 每个ip内，不同情感分类的人数占比，列表中的元素均为object    | 与ip列表的长度相同，同下标元素也对应                       |
| num_ip_person       | list  | 每个ip的人数，列表中的元素均为num    | 与ip列表的长度相同，同下标元素也对应                       |


ip_ratio_per_emotion列表里的对象均遵从如下格式：

| 字段    | 类型 | 内容     | 备注                          |
| ------- | ---- | -------- | ----------------------------- |
| ip    | list  | 这个情感分类里，所有出现的ip，列表中的元素均为字符串   |  |
| num_ip       | list  | 每个ip的人数，列表中的元素均为num    | 与ip列表的长度相同，同下标元素也对应       |


emotion_ratio_per_ip列表里的对象均遵从如下格式：

| 字段    | 类型 | 内容     | 备注                          |
| ------- | ---- | -------- | ----------------------------- |
| emotion    | list  | 这个ip里，所有出现的情感分类，列表中的元素均为字符串   |  |
| num_emotion       | list  | 每个情感分类的人数，列表中的元素均为num    | 与emotion列表的长度相同，同下标元素也对应       |


## 调用爬虫

> /api/v1/crawl

*请求方式：GET
**正文参数：**

| 参数名  | 类型 | 内容                     | 必要性         | 备注                                 |
| ------- | ---- | ------------------------ | -------------- | ------------------------------------ |
| bv      | str  | 视频bv号                | 必要           |   形式：“BVXXXXXX”             |


**效果：**
调用爬虫爬取视频信息，将不会更新到数据库。


**json回复：**
根对象：

| 字段    | 类型 | 内容     | 备注                          |
| ------- | ---- | -------- | ----------------------------- |
| code    | num  | 返回值   | 200：成功<br />407：爬取失败 |
| msg | str  | 错误信息 | 默认为空字符串                       |
| data     | obj  | 数据        |                               |

data对象：

| 字段    | 类型 | 内容     | 备注                          |
| ------- | ---- | -------- | ----------------------------- |
| video       | object  | 视频信息    |        |
| comments       | list  | 评论列表    |                        |

video对象：
| 字段    | 类型 | 内容     | 备注                          |
| ------- | ---- | -------- | ----------------------------- |
| operation_time    | str  | 时间   | 后端处理这条视频的评论的时间，示例："2023-10-08 12:40:32" |
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
| video_desc      | str  | 视频简介     |                        |


comments列表，其中所有对象都以以下格式组织：

| 字段  | 类型 | 内容     | 备注                          |
| ------- | ---- | -------- | ----------------------------- |
| user_uid          |      |   str       |  字符串，但是是数字                        |
| user_name         |      |   str       |                          |
| user_ip           |      |   str       |                          |
| user_sex          |      |  str        | 有“保密”、“男”、“女”   |
| comment_date      |      |  str        | 示例："2023-10-08 12:40:32" |
| comment_text      |      |  str        |                          |
| comment_like      |      |  num        | 点赞数量                         |
| comment_reply     |      |  num        | 回复数量                         |
