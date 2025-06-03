/**
* v-copyText 复制文本内容
*/

// 定义指令接口
interface CopyTextHTMLElement extends HTMLElement {
  $copyCallback?: (text: string) => void;
  $copyValue?: string;
  $destroyCopy?: () => void;
}

// 定义复制选项接口
interface CopyOptions {
  target?: HTMLElement;
}

export default {
  beforeMount(el: CopyTextHTMLElement, { value, arg }: { value: any; arg?: string }) {
    if (arg === "callback") {
      el.$copyCallback = value;
    } else {
      el.$copyValue = value;
      const handler = () => {
        copyTextToClipboard(el.$copyValue as string);
        if (el.$copyCallback) {
          el.$copyCallback(el.$copyValue as string);
        }
      };
      el.addEventListener("click", handler);
      el.$destroyCopy = () => el.removeEventListener("click", handler);
    }
  }
}

function copyTextToClipboard(input: string, { target = document.body }: CopyOptions = {}): boolean {
  const element = document.createElement('textarea');
  const previouslyFocusedElement = document.activeElement as HTMLElement | null;

  element.value = input;

  // Prevent keyboard from showing on mobile
  element.setAttribute('readonly', '');

  element.style.contain = 'strict';
  element.style.position = 'absolute';
  element.style.left = '-9999px';
  element.style.fontSize = '12pt'; // Prevent zooming on iOS

  const selection = document.getSelection();
  const originalRange = selection && selection.rangeCount > 0 ? selection.getRangeAt(0) : null;

  target.append(element);
  element.select();

  // Explicit selection workaround for iOS
  element.selectionStart = 0;
  element.selectionEnd = input.length;

  let isSuccess = false;
  try {
    isSuccess = document.execCommand('copy');
  } catch { }

  element.remove();

  if (originalRange && selection) {
    selection.removeAllRanges();
    selection.addRange(originalRange);
  }

  // Get the focus back on the previously focused element, if any
  if (previouslyFocusedElement) {
    previouslyFocusedElement.focus();
  }

  return isSuccess;
}