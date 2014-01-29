package adt;

/**
 * Lớp {@link KStack} thể hiện một stack đơn giản gồm các thao tác
 * <p>
 * <li> {@link KStack#push(Object)} Thêm phần tử vào đỉnh stack
 * <li> {@link KStack#pop()} Lấy phần tử khỏi đỉnh stack
 * <li> {@link KStack#isEmpty()} Kiểm tra stack rỗng
 * <li> {@link KStack#top()} Trả về phần tử ở đỉnh mà không lấy khỏi stack
 * 
 * @author KhoaHV
 * @version 1.0
 * @param <T>
 * @see KNode
 * @see KQueue
 */
public class KStack<T> extends KNode<T> {

	/**
	 * Kích thước tối đa của stack, không thể thay đổi sau khi khởi tạo stack
	 * 
	 * @see KStack
	 * @see KStack#size
	 */
	private long maxSize;

	/**
	 * Kích thước hiện thời của stack.
	 * 
	 * @see KStack
	 * @see KStack#maxSize
	 */
	private long size;

	/**
	 * Hàm trả về phần tử ở đỉnh stack mà không lấy nó ra khỏi stack
	 * 
	 * @return trả về phần tử ở đỉnh stack
	 * @see KStack
	 * @see KStack#pop()
	 * @see KStack#push(Object)
	 */
	public T top() throws EmptyStackException {
		if (!isEmpty()) {
			return super.getLast().getData();
		}
		throw new EmptyStackException("Cannot top if stack is empty !");
	}

	/**
	 * Kiểm tra xem stack có rỗng không
	 * 
	 * @return <b>true</b> nếu stack rỗng, <b>false</b> nếu ngược lại
	 * @see KStack
	 * @see KStack#getSize()
	 * @see KStack#isFull()
	 */
	public boolean isEmpty() {
		if (size <= 0)
			return true;
		return false;
	}

	/**
	 * Hàm trả về số phần tử hiện có trong stack
	 * 
	 * @return kích thước hiện tại của stack
	 * @see KStack
	 * @see KStack#getMaxSize()
	 * @see KStack#size
	 */
	public long getSize() {
		return size;
	}

	/**
	 * Lấy phần tử ở đỉnh ra khỏi stack
	 * 
	 * @return T đối tượng của phần tử
	 * @see KStack
	 * @see KStack#push(Object)
	 * @see KStack#top()
	 */
	public T pop() throws EmptyStackException {
		if (!isEmpty()) {
			size--;
			return super.takeLast();
		} else {
			throw new EmptyStackException("Cannot pop if stack is empty !");
		}

	}
	
	/**
	 * Lấy đối tượng ở đỉnh stack ra và gán cho <b>data</b>
	 * @param data
	 * @throws EmptyStackException
	 * @see KStack
	 * @see KStack#pop()
	 * @see KStack#push(Object)
	 */
	public void pop(T data) throws EmptyStackException {
		if (!isEmpty()) {
			size--;
			data = super.takeLast();
		} else {
			throw new EmptyStackException("Cannot pop if stack is empty !");
		}

	}

	/**
	 * Đặt một đối tượng vào đỉnh stack
	 * 
	 * @param data
	 * @return <b>true</b> nếu thành công và <b>false</b> nếu thất bại
	 * @see KStack
	 * @see KStack#pop()
	 * @see KStack#top()
	 */
	public void push(T data) throws FullStackException {
		if (!isFull()) {
			super.addToLast(data);
			size++;
		} else {
			throw new FullStackException("Cannot push if stack is full !");
		}
	}

	/**
	 * Hàm kiểm tra xem stack đã đầy chưa.
	 * 
	 * @return <b>true</b> nếu stack đã đầy, <b>false</b> nếu stack chưa đầy
	 * @see KStack
	 * @see KStack#isEmpty()
	 */
	public boolean isFull() {
		if (super.size() >= maxSize)
			return true;
		return false;
	}

	/**
	 * Phương thức khởi tạo một danh sách rỗng. Không khuyên dùng. Kích thước
	 * tối đa mặc định là 1024
	 * 
	 * @see KStack
	 */
	public KStack() {
		super();
		maxSize = 204800;
		size = 0;
	}

	/**
	 * Phương thức khởi tạo một danh sách rỗng với kích thước tối đa
	 * <b>initSize</b>
	 * 
	 * @param initSize
	 *            - kích thước tối đa muốn khởi tạo của stack
	 * @see KStack
	 */
	public KStack(long initSize) {
		super();
		maxSize = initSize;
		size = 0;
	}

	/**
	 * Trả về kích thước tối đã của stack
	 * 
	 * @return {@link KStack#maxSize}
	 * @see KStack
	 * @see KStack#size
	 */
	public long getMaxSize() {
		return maxSize;
	}

}
