# Finding 모듈 함께 개발

## git submodule add로 수정하고 싶은 프로젝트 추가하기
```
git submodule add https://github.com/sarang628/Finding.git
git submodule add https://github.com/sarang628/Map.git
git submodule add https://github.com/sarang628/CardInfo.git
git submodule add https://github.com/sarang628/Filter.git
git submodule add https://github.com/sarang628/TorangRepository.git
```

## 모듈 라이브러리 프로젝트 추가하기
```
rootProject.name = "FindingLinkModules"
include ':app'
include(':finding')
project(':finding').projectDir = new File('./Finding/library')
include(':map')
project(':map').projectDir = new File('./Map/library')
include(':cardInfo')
project(':cardInfo').projectDir = new File('./CardInfo/library')
include(':filter')
project(':filter').projectDir = new File('./Filter/library')
```