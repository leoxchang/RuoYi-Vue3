export interface HamburgerProps {
  isActive?: boolean;

}

export interface HamburgerEmits {
  (e: 'toggleClick'): void;
}
