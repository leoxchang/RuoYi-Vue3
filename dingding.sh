#!/bin/bash
function sendJobStart() {
  curl -X POST -H "Content-Type: application/json" \
        -d '{
              "msgtype": "text",
              "text": {
                  "content": "🟢开始构建：'$CI_PROJECT_TITLE'，分支: '$CI_COMMIT_BRANCH'， 任务：'$CI_JOB_NAME'"
              }
            }' \
        "$DINGTALK_WEBHOOK"
}

function sendJobEnd() {
  echo "$CI_JOB_STATUS"
  if [[ "$CI_JOB_STATUS" == "success" ]]; then
      curl -X POST -H "Content-Type: application/json" \
          -d '{
                "msgtype": "text",
                "text": {
                    "content": "🚀 构建成功：'$CI_PROJECT_TITLE'，分支: '$CI_COMMIT_BRANCH'， 任务：'$CI_JOB_NAME'"
                }
              }' \
          "$DINGTALK_WEBHOOK"
  else
      curl -X POST -H "Content-Type: application/json" \
          -d '{
                "msgtype": "text",
                "text": {
                  "content": "❌ 构建失败：'$CI_PROJECT_TITLE' 任务未能完成，任务：'$CI_JOB_NAME'，请及时查看。"
                }
              }' \
          "$DINGTALK_WEBHOOK"
  fi
}

echo $job_name
if [ "$job_name" = "job_start" ]; then
    sendJobStart
elif [ "$job_name" = "job_end" ]; then
    sendJobEnd
fi
