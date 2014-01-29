package adt;

/**
 * Lớp {@link KQueue} thể hiện một hàng đợi đơn giản gồm các thao tác
 * <p>
 * <li> {@link KQueue#enQueue(Object)} Đưa một phần tử vào hàng đợi
 * <li> {@link KQueue#deQueue()} Đưa một phần tử ra khỏi hàng đợi
 * <li> {@link KQueue#isEmpty()} Kiểm tra hàng đợi rỗng
 * 
 * @author KhoaHV
 * @version 1.0
 * @param <T>
 * @see KNode
 * @see KStack
 */
public class KQueue<T> extends KNode<T> {

	/**
	 * Kích thước hiện tại của hàng đợi.
	 * 
	 * @see KQueue
	 */
	private long size;

	/**
	 * Hàm khởi tạo mặc định.
	 * 
	 * @see KQueue
	 */
	public KQueue() {
		super();
		size = 0;
	}

	/**
	 * Kiểm tra hàng đợi rỗng.
	 * 
	 * @see KQueue
	 * @return <b>true</b> nếu hàng đợi rỗng, <b>false</b> nếu ngược lại.
	 */
	public boolean isEmpty() {
		if (size <= 0)
			return true;
		return false;
	}

	/**
	 * Đưa một đối tượng vào hàng đợi.
	 * 
	 * @param data
	 * @see KQueue
	 */
	public void enQueue(T data) {
		super.addToLast(data);
		size++;
	}

	/**
	 * Đưa một đối tượng ra khỏi hàng đợi
	 * 
	 * @return T
	 * @see KQueue
	 */
	public T deQueue() throws EmptyQueueException {
		if (isEmpty()) {
			throw new EmptyQueueException("Cannot dequeue if Queue is empty !");
		}
		size--;
		return super.takeFirst();
	}

	/**
	 * Trả về kích thước hiện tại của hàng đợi
	 * 
	 * @return kích thước hiện tại của hàng đợi
	 * @see KQueue
	 */
	public long getSize() {
		return size;
	}

}
