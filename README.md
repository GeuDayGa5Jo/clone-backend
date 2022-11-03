<img src="https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2F20130122_106%2Ftensaiseung_1358842624770erdHE_PNG%2F%25C6%25AE%25C0%25A7%25C5%25CD.png&type=sc960_832" width="200" height="200"/>


### clone-backend

# 트위터 클론 프로잭트


서버 URL : http://twitter-clone-aws-bucket.s3-website.ap-northeast-2.amazonaws.com/

영상 URL :

클론 프로잭트 팀 노션 : https://www.notion.so/5-5-6bb6e460bbb94acbb622bc7ab22e79e5

---
<h2>🚀 구현된 기능</h2>

    회원 가입 및 로그인
    
    개인 정보 수정 (프로필 사진 , 헤더 사진, 상태 메세지, 별명)
    
    게시글 , 댓글 crud (사진, 내용) 

    게시글, 댓글 좋아요(갯수포함) 팔로우, 카카오 Oauth (백엔드만 구현) 

---
<h2>💻 프로젝트 소개</h2>
<p>어디서나 함께 하는 소셜네트워크(sns) 앱을 클론코딩 해봤습니다</p>
<br />

### 프로 젝트 기간
    10.28 ~ 11.03

---
<h2>👫🏻 팀원 소개 </h2>

| 이름   | github | position |
|------|--------|----------|
| 박성민 | https://github.com/Adam-SungMin-Park   | 백엔드(팀장)     |
| 박영성 | https://github.com/youngsungpark   | 백엔드     |
| 강희인 | https://github.com/Online-abayss   | 백엔드     |
| 박소영 | https://github.com/0001401   | 프론트엔드     |
| 서지운 | https://github.com/MildColor   | 프론트엔드     |

---
### API

![test](https://user-images.githubusercontent.com/83463300/199677835-640cad11-d7be-4f1e-90a5-6323ceaa6402.PNG)

--- ---
<h2>📃 ERD</h2>
![Screen Shot 2022-11-03 at 16 59 54](https://user-images.githubusercontent.com/83463300/199676468-a8948b20-2799-4cf2-8f45-b4068340fa5f.png)


---
### 트러블 슈팅
    1. @ModelAttribu는 게시글 수정 시 , 사진이 포함되어 있어야 수정이 가능 했으며,
    @RequestParam은 위의 과정을 해결하기 위해 하나씩 받아왔으나, 프론트와 인자 값의 순서를 맞추지 않으면,
    결과값이 의도와 다르게 나오는걸, 프론트와 동일하게 맞춰서 해결.
    2. 회원 가입 시 default image를 주기 위해 s3에 업로드된 사진으로 이용했으나,
    재사용에 문제가 있음을 알고, 인터넷 Url을 이용하여 해결   
    3. 협업 과정에서 Entity의 필드값이 추가 되여 수정 할때, database의 column이 반영 되지 않아서,
    작동 하는데 원할이 되지 않았던 부분을 database를 초기화시켜 해결.
---
