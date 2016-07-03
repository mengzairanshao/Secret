这里是几个主要的 API :

版块列表 : http://xxx/api/mobile/index.php?mobile=no&version=1&module=forumindex

版块的主题列表 : http://xxx/api/mobile/index.php?mobile=no&version=1&module=forumdisplay&fid=11  其中的 fid 是上个 API 中返回值中的 fid , 即版块ID, 还可以有 page, tpp (pageSize)

主题详细: http://xxx/api/mobile/index.php?mobile=no&version=1&module=viewthread&tid=3287083 tid 即主题ID, 还可以有 page, ppp (pageSize) , 这名字取的好蛋疼啊.
登陆:  POST /api/mobile/index.php?mobile=no&version=1&module=login&loginsubmit=yes&loginfield=auto&submodule=checkpost
password=密码&username=用户名&formhash=&answer=安全问题答案&questionid=5




个人资料：
http://139.196.29.35:8008/discuz/api/mobile/index.php?version=4&module=profile

论坛的版块列表：
http://139.196.29.35:8008/discuz/api/mobile/index.php?version=4&module=forumnav
http://139.196.29.35:8008/discuz/api/mobile/index.php?version=4&module=forumindex （带图标）
注意：这两个接口都无法获取被隐藏的版块

版块下的主题列表
http://139.196.29.35:8008/discuz/api/mobile/index.php?version=4&module=forumdisplay&fid=2&page=1
http://139.196.29.35:8008/discuz/api/mobile/index.php?version=4&module=toplist&fid=41（版块下的置顶帖子列表）

查看主题（帖子列表）
http://139.196.29.35:8008/discuz/api/mobile/index.php?version=4&module=viewthread&tid=26

发表主题
http://139.196.29.35:8008/discuz/api/mobile/index.php?version=4&module=newthread

发表回复
http://139.196.29.35:8008/discuz/api/mobile/index.php?version=4&module=sendreply

我的帖子
http://139.196.29.35:8008/discuz/api/mobile/index.php?version=4&module=mythread&page=1

热帖
http://139.196.29.35:8008/discuz/api/mobile/index.php?version=4&module=hotthread

新帖
http://139.196.29.35:8008/discuz/api/mobile/index.php?version=4&module=newthreads

消息列表
http://www.mawentao.com/api/mobile/index.php?version=4&module=mynotelist

收藏版块
http://139.196.29.35:8008/discuz//api/mobile/index.php?version=4&module=favforum&id=2&formhash=07e7419a
收藏帖子
http://139.196.29.35:8008/discuz//api/mobile/index.php?version=4&module=favthread&id=85&formhash=07e7419a