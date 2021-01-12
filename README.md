# dnd-mentee-4th-3-frontend

# Git Bash

## github, directory add
∙ git clone 경로
  - 경로에 있는 내용을 복사해온다. (github에 있는 내용을 가져온다.) 이후 cd 명령어로 해당 디렉터리에 들어간다.
∙ git status
  - git 상태확인한다.
∙ git add .
  - 현재 update 된 모든 파일을(stage에 저장) 넣는다.
∙ git commit -m "메세지“
  - 변경사항 업로드 했을 때, 나 또는 다른 팀원에게 알려주는 commit
∙ git remote -v
  - 현재 프로젝트에 등록된 리모트 저장소를 확인
∙ git branch
  - branch 확인
∙ git push origin master
  - github 자체에 *master update 내용을 올리겠다.

## github, directory remove
∙ git rm --cached -r 폴더이름 : 원격 저장소에 있는 파일을 삭제, 로컬 저장소에 있는 파일은 삭제하지 않는다.
∙ git rm 폴더이름 : 원격 저장소와 로컬 저장소에 있는 파일을 삭제한다.
  - git commit -m "github에서 업데이트 확인문“ : 어떤 순간 작업공간의 상태를 저장
  - git push -f origin master : git에 push한다.

