package adt;

/**
 * Lớp {@link KNode} cài đặt danh sách liên kết đơn đơn giản. Một nút gồm có ba
 * trường: <li> {@link KNode#data}: Chứa dữ liệu của nút ở dạng Template (Có thể
 * chứa bất kì đối tượng nào). <li> {@link KNode#index}: Chỉ số của nút trong
 * danh sách. <li> {@link KNode#next}: Nút tiếp theo trong danh sách.
 * <p>
 * Nút đầu tiên có index là 0 và data là null, nút cuối cùng có next là null.
 * <p>
 * <b>Chú ý: Luôn xử lí với phần tử tiếp theo, ví dụ:</b>
 * <p>
 * Duyệt tất cả các phần tử của một danh sách klist:
 * <p>
 * <code>for (KNode&lt;?&gt; it = klist; !it.isLast(); it = it.getNext()) {
 * <p> &nbsp;&nbsp;&nbsp; it.getNext().getData();
 * <p> &nbsp;&nbsp;&nbsp; it.getNext().getIndex();
 * <p>}</code>
 * <p>
 * KNode hỗ trợ các phép toán thông thường trên danh sách liên kết đơn như:
 * <li>{@link #add(Object)} Bổ sung thêm phần tử vào đầu danh sách.
 * <li>{@link #addToLast(Object)} Bổ sung thêm phần tử vào cuối danh sách.
 * <li>{@link #takeFirst()} Lấy phần tử đầu tiên khỏi danh sách.
 * <li>{@link #takeLast()} Lấy phần tử cuối cùng khỏi danh sách.
 * <li>{@link #size()} Trả về số phần tử của danh sách.
 * 
 * @author KhoaHV
 * @version 1.0
 * @see Object
 * @see KQueue
 * @see KStack
 */
public class KNode<T> {

	// Attributes:
	/**
	 * Trường {@link KNode#data} có thể chứa đối tượng bất kì.
	 * 
	 * @see KNode
	 * 
	 * */
	private T data;
	/**
	 * Trường {@link KNode#index} chứa chỉ số của phần tử trong danh sách <b>
	 * <p>
	 * Chú ý:
	 * <li>Phần tử thực sự của danh sách luôn có index >= 1
	 * <li>Luôn xử lí bằng phần tử tiếp theo, ví dụ: </b>
	 * <p>
	 * <code>for (KNode&lt;?&gt; it = klist; !it.isLast(); it = it.getNext()) {
	 * <p> &nbsp;&nbsp;&nbsp; it.getNext().getData();
	 * <p> &nbsp;&nbsp;&nbsp; it.getNext().getIndex();
	 * <p>}</code>
	 * 
	 * @see KNode
	 */
	private long index;

	/**
	 * Trường {@link KNode#next} chứa phần tử tiếp theo trong danh sách
	 * <p>
	 * <b>Chú ý:
	 * <li>Phần tử đầu danh sách luôn có index == 1
	 * <li>Phần tử cuối danh sách luôn có next == null</b>
	 */
	private KNode<T> next;

	// Methods:

	/**
	 * Hàm khởi tạo mặc định để tạo một danh sách. Khuyên luôn chỉ dùng hàm này.
	 * 
	 * @see KNode
	 * @see KNode#add(Object)
	 */
	public KNode() {
		this.index = 0;
		this.data = null;
		this.next = null;
	}

	/**
	 * Hàm khởi tạo một nút cô lập có chứa dữ liệu
	 * 
	 * @param data
	 * @see KNode
	 */
	protected KNode(T data) {
		this.index = 0;
		this.data = data;
		this.next = null;
	}

	/**
	 * Lấy ra phần tử cuối cùng của danh sách
	 * 
	 * @return phần tử cuối cùng của danh sách
	 * @see KNode
	 * @see KNode#take(long)
	 * @see KNode#takeFirst()
	 */
	public T takeLast() {
		if (size() > 0)
			return take(this.getLast().getIndex());
		return null;
	}

	/**
	 * Lấy ra phần tử đầu tiên của danh sách
	 * 
	 * @return phần tử đầu tiên của danh sách
	 * @see KNode
	 * @see KNode#take(long)
	 * @see KNode#takeLast()
	 */
	public T takeFirst() {
		if (size() > 0)
			return take(1);
		return null;
	}

	/**
	 * Lấy một phần tử ở vị trí {@link KNode#index} ra khỏi danh sách
	 * 
	 * @param index
	 *            - vị trí của phần tử trong danh sách
	 * @return T - giá trị của phần tử ở vị trí index
	 * @see KNode
	 * @see KNode#takeLast()
	 * @see KNode#takeFirst()
	 */
	public T take(long index) {
		// node tham chiếu đến nút muốn lấy
		KNode<T> node = get(index);
		if (node == null)
			return null;
		// beforeNode tham chiếu đến nút ngay trước nút muốn lấy
		KNode<T> beforeNode = this.getBefore(index);
		beforeNode.next = node.next; // ngắt node khỏi danh sách
		// Chỉnh lại index
		beforeNode.renumber();
		return node.getData();
	}

	/**
	 * Đánh số lại danh sách
	 * 
	 * @see KNode
	 */
	public void renumber() {
		for (KNode<T> it = this; !it.isLast(); it = it.next) {
			it.next.index = it.index + 1;
		}
	}

	/**
	 * 
	 * @return size - số phần tử thực sự của danh sách
	 * @see KNode
	 */
	public long size() {
		long size = 0;
		for (KNode<T> it = this; !it.isLast(); it = it.next, size++)
			;
		return size;
	}

	/**
	 * Trả về phần tử trước phần tử có chỉ số index. Trả về <b>null</b> nếu
	 * không có phần tử nào như vậy.
	 * 
	 * @param index
	 * @return KNode
	 * @see KNode
	 * @see KNode#get(long)
	 * @see KNode#getFirst()
	 * @see KNode#getLast()
	 */
	public KNode<T> getBefore(long index) {
		if (index <= 0 || index > size())
			return null;
		//if (this.index == index) return this;
		for (KNode<T> it = this; !it.isLast(); it = it.next) {
			if (it.next.index == index)
				return it;
		}
		return null;
	}

	/**
	 * Trả về nút đầu tiên của danh sách (không tính nút có index == 0)
	 * 
	 * @return nút đầu tiên của danh sách
	 * @see KNode
	 * @see KNode#get(long)
	 * @see KNode#getBefore(long)
	 * @see KNode#getLast()
	 */
	public KNode<T> getFirst() {
		if (size() > 0)
			return get(1);
		return null;
	}

	/**
	 * Trả về phần tử ở vị trí {@link KNode#index}. Trả về <b>null</b> nếu không
	 * có phần tử như vậy.
	 * 
	 * @param index
	 *            - vị trí của phần tử trong danh sách
	 * @return KNode - nút ở vị trí index
	 * @see KNode
	 * @see KNode#getBefore(long)
	 * @see KNode#getFirst()
	 * @see KNode#getLast()
	 */
	public KNode<T> get(long index) {
		if (index <= 0 || index > size())
			return null;
		if (this.index == index) return this;
		for (KNode<T> it = this; !it.isLast(); it = it.next) {
			if (it.next.index == index)
				return it.next;
		}
		return null;
	}

	/**
	 * Thêm vào cuối danh sách
	 * 
	 * @param data
	 *            là kiểu dữ liệu bất kì
	 * @see KNode
	 * @see KNode#add(Object)
	 * @see KNode#addAfter(long, Object)
	 */
	public void addToLast(T data) {
		KNode<T> temp = this.getLast();
		temp.next = new KNode<T>();
		temp.next.index = temp.index + 1;
		temp.next.data = data;
	}

	/**
	 * Thêm vào sau phần tử ở vị trí <b>index</b>
	 * 
	 * @param index
	 *            - vị trí cần chèn vào sau
	 * @param data
	 *            - Giá trị của phần tử cần chèn
	 * @return <b>true</b> nếu thành công và <b>false</b> nếu thất bại
	 * @see KNode
	 * @see KNode#add(Object)
	 * @see KNode#addToLast(Object)
	 */
	public boolean addAfter(long index, T data) {
		if (index < 1 || index > size())
			return false;
		KNode<T> before = get(index);
		KNode<T> node = new KNode<T>(data);
		node.next = before.next;
		before.next = node;
		before.renumber();
		return true;
	}

	/**
	 * Thêm vào đầu danh sách
	 * 
	 * @param data
	 *            là kiểu dữ liệu bất kì
	 * @see KNode
	 * @see KNode#addToLast(Object)
	 * @see KNode#addAfter(long, Object)
	 */
	public void add(T data) {
		KNode<T> temp = getLast();
		temp.next = new KNode<T>();
		temp.next.index = temp.index + 1;
		temp.next.data = data;
	}

	/**
	 * Trả về <b>true</b> nếu là phần tử cuối cùng, <b>false</b> nếu chưa phải
	 * là phần tử cuối cùng.
	 * 
	 * @return boolean
	 * @see KNode
	 * @see KNode#isHead()
	 */
	public boolean isLast() {
		if (this.next == null)
			return true;
		return false;
	}

	/**
	 * Trả về <b>true</b> nếu là phần tử đánh dấu đầu danh sách, <b>false</b>
	 * nếu ngược lại.
	 * 
	 * @return boolean
	 * @see KNode
	 */
	public boolean isHead() {
		if (this.index == 0)
			return true;
		return false;
	}

	/**
	 * Trả về phần tử cuối cùng của danh sách
	 * 
	 * @return KNode
	 * @see KNode#get(long)
	 * @see KNode#getBefore(long)
	 * @see KNode#getFirst()
	 */
	public KNode<T> getLast() {
		KNode<T> temp = this;
		while (temp.next != null) {
			temp = temp.next;
		}
		return temp;
	}

	/**
	 * Trả về chỉ số của phần tử hiện tại. Nên chỉ dùng hàm này khi duyệt danh
	 * sách
	 * 
	 * @return chỉ số của phần tử hiện tại
	 * @see KNode
	 */
	public long getIndex() {
		if (this == null)
			return -1;
		return index;
	}

	/**
	 * Hàm nội bộ không sử dụng
	 * 
	 * @param index
	 * @see KNode
	 */
	@SuppressWarnings("unused")
	private void setIndex(long index) {
		this.index = index;
	}

	/**
	 * Trả về phần tử tiếp theo mà nút gọi chỉ đến. Chỉ dùng khi duyệt danh sách
	 * <p><b>Chú ý: Luôn duyệt theo phần tử tiếp theo</b>
	 * @return Nút tiếp theo
	 * @see KNode
	 */
	public KNode<T> getNext() {
		return next;
	}

	/**
	 * Hàm nội bộ không sử dụng
	 * 
	 * @param next
	 * @see KNode
	 */
	@SuppressWarnings("unused")
	private void setNext(KNode<T> next) {
		this.next = next;
	}

	/**
	 * Trả về giá trị của nút hiện tại. Chỉ dùng khi duyệt danh sách.
	 * @return Giá trị của phần tử hiện tại gọi hàm
	 * @see KNode
	 */
	public T getData() {
		return data;
	}

	/**
	 * Đặt lại giá trị cho nút hiện tại. Chỉ dùng khi duyệt danh sách.
	 * @param data
	 * @see KNode
	 */
	public void setData(T data) {
		this.data = data;
	}
	
	/**
	 * Đặt lại giá trị cho nút ở vị trí index.
	 * @param index - vị trí cần thay đổi giá trị
	 * @param data
	 * @see KNode
	 * @return <b>true</b> nếu thành công và <b>false</b> nếu thất bại
	 */
	public boolean setData(long index, T data) {
		if (index <= 0 || index > size())
			return false;
		get(index).data = data;
		return true;
	}

}

